package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.SensorDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor implements IEntity<Sensor>{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String description;

    @Column
    private Long maxValue;

    @Override
    public Sensor toEntity(IDto<Sensor> dto) {
        SensorDto sensorDto = (SensorDto) dto;
        return Sensor.builder()
                .id(sensorDto.getId())
                .description(sensorDto.getDescription())
                .maxValue(sensorDto.getMaxValue())
                .build();
    }
}
