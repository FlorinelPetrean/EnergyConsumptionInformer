package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.SensorDto;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.MERGE) //do not change this
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<Record> records;

    @Override
    public Sensor toEntity(IDto<Sensor> dto) {
        SensorDto sensorDto = (SensorDto) dto;
        Device device = Device.builder()
                .id(sensorDto.getDeviceId())
                .build();
        return Sensor.builder()
                .id(sensorDto.getId())
                .description(sensorDto.getDescription())
                .maxValue(sensorDto.getMaxValue())
                .device(device)
                .records(new ArrayList<>(100))
                .build();
    }
}
