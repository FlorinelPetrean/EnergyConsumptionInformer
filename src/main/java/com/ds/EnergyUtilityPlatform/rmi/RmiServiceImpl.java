package com.ds.EnergyUtilityPlatform.rmi;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.rmi.model.RecordChart;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.rmi.model.AppUserRmi;
import com.ds.EnergyUtilityPlatform.rmi.model.ChartRecords;
import com.ds.EnergyUtilityPlatform.rmi.model.DeviceRmi;
import com.ds.EnergyUtilityPlatform.service.DeviceService;
import com.ds.EnergyUtilityPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RmiServiceImpl implements RmiService{
    private final DeviceService deviceService;
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @Override
    public String sendMessage(String msg) {
        System.out.println("================Server Side ========================");
        System.out.println("Inside Rmi IMPL - Incoming msg : " + msg);
        return "Hello " + msg + " :: Response time - > " + new Date();
    }

    @Override
    public AppUserRmi login(String username, String password) {
        AppUser user = userService.login(username, password);
        List<DeviceRmi> devices = user.getDevices().stream()
                .map(device -> DeviceRmi.builder()
                        .id(device.getId())
                        .address(device.getAddress())
                        .description(device.getDescription())
                        .avgEnergyConsumption(device.getAvgEnergyConsumption())
                        .maxEnergyConsumption(device.getMaxEnergyConsumption())
                        .build())
                .collect(Collectors.toList());
        return AppUserRmi.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .devices(devices)
                .build();
    }



    public double[] getDeviceHourlyEnergyConsumptionInDay(List<Record> records, LocalDate day) {
        records = records.stream()
                .filter(r -> r.getDate().toLocalDate().isEqual(day))
                .collect(Collectors.toList());


        double[] hourlyEnergyConsumptionArray = new double[24];
        int[] hourlyEnergyConsumptionArrayCount = new int[24];
        for(Record record : records) {
            LocalDateTime recordDay = record.getDate();
            int hour = recordDay.getHour();
            hourlyEnergyConsumptionArray[hour] += record.getEnergyConsumption();
            hourlyEnergyConsumptionArrayCount[hour]++;
        }

        for(int i = 0; i < 24; i++) {
            hourlyEnergyConsumptionArray[i] /= hourlyEnergyConsumptionArrayCount[i];
        }

        return hourlyEnergyConsumptionArray;
    }

    @Override
    public ChartRecords getDeviceHourlyEnergyConsumptionOverDays(Long deviceId, Long days) {
        List<RecordChart> chartRecords = new ArrayList<>();
        Device device = deviceService.findById(deviceId);
        List<Record> records = device.getSensor().getRecords();
        LocalDate filterDate = records.get(records.size() - 1).getDate().toLocalDate().minusDays(days + 1);

        for(long i = 0; i < days; i++) {
            LocalDate date = filterDate.plusDays(i);
            Double energyConsumption = Arrays.stream(getDeviceHourlyEnergyConsumptionInDay(records, date))
                    .sum() / 24;
            RecordChart chartRecord = new RecordChart(date.toString(), energyConsumption);
            chartRecords.add(chartRecord);
        }

        return new ChartRecords(chartRecords);
    }



    @Override
    public Double getAverageEnergyConsumptionOverWeek(Long deviceId, Long hour) {
        long days = 7L;

        Device device = deviceService.findById(deviceId);
        List<Record> records = device.getSensor().getRecords();
        LocalDate filterDate = records.get(records.size() - 1).getDate().toLocalDate().minusDays(days + 1);

        double baseline = 0.0;
        for(long i = 0; i < days; i++) {
            LocalDate date = filterDate.plusDays(i);
            int h = Math.toIntExact(hour);
            double hourlyEnergyConsumption = getDeviceHourlyEnergyConsumptionInDay(records, date)[h];
            baseline += hourlyEnergyConsumption;
        }
        baseline /= days;

        return baseline;
    }


    public Double getMaxEnergyConsumptionOverWeek(Long deviceId, Long hour) {
        long days = 7L;

        Device device = deviceService.findById(deviceId);
        List<Record> records = device.getSensor().getRecords();
        LocalDate filterDate = records.get(records.size() - 1).getDate().toLocalDate().minusDays(days + 1);

        double maxValue = 0.0;
        for(long i = 0; i < days; i++) {
            LocalDate date = filterDate.plusDays(i);
            int h = Math.toIntExact(hour);
            double hourlyEnergyConsumption = getDeviceHourlyEnergyConsumptionInDay(records, date)[h];
            if (maxValue < hourlyEnergyConsumption) {
                maxValue = hourlyEnergyConsumption;
            }
        }
        return maxValue;
    }


    @Override
    public Long getOptimalHour(Long deviceId, Long programDuration) {
        Device device = deviceService.findById(deviceId);
        double[] baselines = new double[24];
        double[] maxValues = new double[24];
        for(int i = 0; i < 24; i++){
            baselines[i] = getAverageEnergyConsumptionOverWeek(deviceId, (long) i);
            maxValues[i] = device.getMaxEnergyConsumption();
        }
        long optimalHour = 0L;
        double minimum = Double.MAX_VALUE;
        List<RecordChart> records = new ArrayList<>();

        for(int i = 0; i < 24 - programDuration; i++) {
            double currentMinimum = 0L;
            for(int h = i; h < i + programDuration; h++) {
                if(currentMinimum < baselines[h] + maxValues[i]) {
                    currentMinimum = baselines[h] + maxValues[i];
                }
            }

            if (minimum > currentMinimum) {
                minimum = currentMinimum;
                optimalHour = i;
            }
        }

        return optimalHour;
    }

    @Override
    public ChartRecords getProgramChart(Long deviceId, Long programHours) {
        long days = 7L;
        Long optimalHour = getOptimalHour(deviceId, programHours);

        Device device = deviceService.findById(deviceId);
        List<Record> records = device.getSensor().getRecords();

        List<RecordChart> recordsChart = new ArrayList<>();

        double[] baselines = new double[24];
        double[] maxValues = new double[24];
        for(int i = 0; i < 24; i++){
            baselines[i] = getAverageEnergyConsumptionOverWeek(deviceId, (long) i);
            maxValues[i] = device.getMaxEnergyConsumption();
        }

        for(long hour = optimalHour; hour < optimalHour + programHours; hour++) {
            String hourString = hour + ":00";
            int h = (int) hour;
            double energyConsumption = baselines[h] + maxValues[h];
            RecordChart recordChart = new RecordChart(hourString, energyConsumption);
            recordsChart.add(recordChart);
        }


        return new ChartRecords(recordsChart);
    }
}
