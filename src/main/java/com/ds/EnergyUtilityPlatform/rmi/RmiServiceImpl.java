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
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
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

    @Override
    public ChartRecords getDeviceHourlyEnergyConsumptionOverDays(Long deviceId, Long days, Long hour) {
        List<RecordChart> chartRecords = new ArrayList<>();
//        LocalDate filterDate = LocalDate.now(ZoneOffset.UTC).minusDays(days);
        Device device = deviceService.findById(deviceId);
        List<Record> records = device.getSensor().getRecords();
        LocalDate filterDate = records.get(records.size() - 1).getDate().toLocalDate().minusDays(days + 1);
        records = records.stream()
                .filter(r -> r.getDate().toLocalDate().isAfter(filterDate) &&
                        r.getDate().toLocalTime().getHour() == hour)
                .collect(Collectors.toList());

        int hourlyCount = 0;
        double hourlyEnergyConsumption = 0.0;
        LocalDate currentDay = records.get(0).getDate().toLocalDate();
        for(Record record : records) {
            LocalDate recordDay = record.getDate().toLocalDate();
            if (currentDay.isEqual(recordDay)) {
                hourlyCount += 1;
                hourlyEnergyConsumption += record.getEnergyConsumption();
            }
            else {
                String localDate = recordDay.toString();
                hourlyEnergyConsumption /= hourlyCount;
                RecordChart chartRecord = new RecordChart(localDate, hourlyEnergyConsumption);
                chartRecords.add(chartRecord);

                hourlyEnergyConsumption = record.getEnergyConsumption();
                hourlyCount = 1;
                currentDay = currentDay.plusDays(1);
            }

        }

        return new ChartRecords(chartRecords);
    }

    @Override
    public Double getAverageEnergyConsumptionOverWeek(Long deviceId, Long hour) {
        long days = 7L;
        List<RecordChart> recordCharts = getDeviceHourlyEnergyConsumptionOverDays(deviceId, days, hour).getRecords();
        return recordCharts.stream()
                .map(RecordChart::getEnergyConsumption)
                .reduce(0.0, Double::sum) / days;
    }


    public Double getMaxEnergyConsumptionOverWeek(Long deviceId, Long hour) {
        long days = 7L;
        double baseline = getAverageEnergyConsumptionOverWeek(deviceId, hour);
        List<RecordChart> recordCharts = getDeviceHourlyEnergyConsumptionOverDays(deviceId, days, hour).getRecords();
        return recordCharts.stream()
                .map(r -> r.getEnergyConsumption() + baseline)
                .reduce(0.0, Double::max);
    }

    @Override
    public ChartRecords getProgramChart(Long deviceId, Long programHours) {
        long days = 7L;
        long optimalHour = 0L;
        double minimum = Double.MAX_VALUE;
        List<RecordChart> records = new ArrayList<>();

        for(long i = 0; i < 24 - programHours; i++) {
            double currentMinimum = 0L;
            for(long h = i; h < i + programHours; h++) {
                double maximum = getMaxEnergyConsumptionOverWeek(deviceId, h);
                currentMinimum += maximum;
            }
            currentMinimum /= programHours;

            if (minimum > currentMinimum) {
                minimum = currentMinimum;
                optimalHour = i;
            }
        }
        for(long i = 0; i < 24; i++) {
            RecordChart recordChart = new RecordChart("", 0.0);
            if (i >= optimalHour && i <= optimalHour + programHours)
                recordChart.setEnergyConsumption(minimum);
            else
                recordChart.setEnergyConsumption(getAverageEnergyConsumptionOverWeek(deviceId, i));
            recordChart.setTimestamp(i + ":00");

            records.add(recordChart);
        }

        return new ChartRecords(records);
    }
}
