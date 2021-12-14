package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.entitydto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.entitydto.RecordDto;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.*;


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
    private Long timestamp;

    @Column
    private Double energyConsumption;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;


    public LocalDateTime getDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this.timestamp), ZoneOffset.UTC);
    }


    @Override
    public Record toEntity(IDto<Record> dto) {
        RecordDto recordDto = (RecordDto) dto;
        Sensor sensor = Sensor.builder()
                .id(recordDto.getSensorId())
                .build();
        ZonedDateTime zdt = ZonedDateTime.now();
        return Record.builder()
                .energyConsumption(recordDto.getEnergyConsumption())
                .timestamp(Long.valueOf(recordDto.getTimestamp()))
                .sensor(sensor)
                .build();
    }


}
