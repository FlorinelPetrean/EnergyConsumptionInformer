package com.ds.EnergyUtilityPlatform.rmi;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.RecordChart;
import com.ds.EnergyUtilityPlatform.model.dto.entitydto.RecordDto;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.service.DeviceService;
import com.ds.EnergyUtilityPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public AppUser login(String username, String password) {
        return userService.login(username, password);
    }

    @Override
    public List<RecordChart> getDeviceHourlyEnergyConsumptionOverDays(Long deviceId, Long days, Long hour) {
        List<RecordChart> chartRecords = new ArrayList<>();
        LocalDate filterDate = LocalDate.now(ZoneOffset.UTC).minusDays(days);
        Device device = deviceService.findById(deviceId);
        List<Record> records = device.getSensor().getRecords()
                .stream()
                .filter(r -> r.getDate().isAfter(ChronoLocalDateTime.from(filterDate)) &&
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

        return chartRecords;
    }

    @Override
    public Double getAverageEnergyConsumptionOverWeek(Long deviceId, Long hour) {
        long days = 7L;
        List <RecordChart> recordCharts = getDeviceHourlyEnergyConsumptionOverDays(deviceId, days, hour);
        return recordCharts.stream()
                .map(RecordChart::getEnergyConsumption)
                .reduce(0.0, Double::sum) / days;
    }
}
