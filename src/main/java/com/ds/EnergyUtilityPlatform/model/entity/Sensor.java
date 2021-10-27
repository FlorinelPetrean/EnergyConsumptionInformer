package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.SensorDto;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Sensor implements IEntity<Sensor>{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private Long maxValue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

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
