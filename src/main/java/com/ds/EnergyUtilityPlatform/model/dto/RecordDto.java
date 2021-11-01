package com.ds.EnergyUtilityPlatform.model.dto;


import com.ds.EnergyUtilityPlatform.model.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto implements IDto<Record> {
    private LocalDateTime timestamp;

    private Long energyConsumption;


    @Override
    public IDto<Record> toDto(Record entity) {
        return builder()
                .energyConsumption(entity.getEnergyConsumption())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
