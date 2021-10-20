package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.DeviceDto;
import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device implements IEntity<Device>{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private Long maxEnergyConsumption;

    @Column
    private Float avgEnergyConsumption;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


    @Override
    public Device toEntity(IDto<Device> dto) {
        DeviceDto deviceDto = (DeviceDto) dto;
        return Device.builder()
                .id(deviceDto.getId())
                .description(deviceDto.getDescription())
                .address(deviceDto.getAddress())
                .maxEnergyConsumption(deviceDto.getMaxEnergyConsumption())
                .avgEnergyConsumption(deviceDto.getAvgEnergyConsumption())
                .build();
    }
}
