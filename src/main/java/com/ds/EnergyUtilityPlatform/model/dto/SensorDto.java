package com.ds.EnergyUtilityPlatform.model.dto;


import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto implements IDto<Sensor>{
    private Long id;

    private String description;

    private Long maxValue;

    public SensorDto toDto(Sensor sensor) {
        return SensorDto.builder()
                .id(sensor.getId())
                .description(sensor.getDescription())
                .maxValue(sensor.getMaxValue())
                .build();
    }
}
