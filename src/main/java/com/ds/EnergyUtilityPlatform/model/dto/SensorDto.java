package com.ds.EnergyUtilityPlatform.model.dto;


import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto implements IDto<Sensor>{
    private Long id;

    private String description;

    private Long maxValue;

    private Long deviceId;

    public SensorDto toDto(Sensor sensor) {
        Long deviceId = null;
        if (sensor.getDevice() != null)
            deviceId = sensor.getDevice().getId();

        return SensorDto.builder()
                .id(sensor.getId())
                .description(sensor.getDescription())
                .maxValue(sensor.getMaxValue())
                .deviceId(deviceId)
                .build();
    }
}
