package com.ds.EnergyUtilityPlatform.model.dto;


import com.ds.EnergyUtilityPlatform.model.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto implements IDto<Record> {

    private Long id;

    private String timestamp;

    private Double energyConsumption;

    private Long sensorId;


    @Override
    public IDto<Record> toDto(Record record) {
        Long sensorId = null;
        if (record.getSensor() != null)
            sensorId = record.getSensor().getId();
        DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
        return builder()
                .id(record.getId())
                .energyConsumption(record.getEnergyConsumption())
                .timestamp(record.getTimestamp().format(format))
                .sensorId(sensorId)
                .build();
    }
}
