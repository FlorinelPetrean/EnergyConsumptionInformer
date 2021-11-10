package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.RecordDto;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record implements IEntity<Record>{
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Long energyConsumption;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;


    @Override
    public Record toEntity(IDto<Record> dto) {
        RecordDto recordDto = (RecordDto) dto;
        Sensor sensor = Sensor.builder()
                .id(recordDto.getSensorId())
                .build();
        return Record.builder()
                .energyConsumption(recordDto.getEnergyConsumption())
                .sensor(sensor)
                .build();
    }
}
