package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.DeviceDto;
import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Device implements IEntity<Device>{
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private Double maxEnergyConsumption;

    @Column
    private Double avgEnergyConsumption;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private AppUser user;




    @Override
    public Device toEntity(IDto<Device> dto) {
        DeviceDto deviceDto = (DeviceDto) dto;
        AppUser user = AppUser.builder()
                .username(deviceDto.getUsername())
                .build();
        Sensor sensor = Sensor.builder()
                .id(deviceDto.getSensorId())
                .build();
        return Device.builder()
                .id(deviceDto.getId())
                .description(deviceDto.getDescription())
                .address(deviceDto.getAddress())
                .maxEnergyConsumption(deviceDto.getMaxEnergyConsumption())
                .avgEnergyConsumption(deviceDto.getAvgEnergyConsumption())
                .user(user)
                .sensor(sensor)
                .build();
    }
}
