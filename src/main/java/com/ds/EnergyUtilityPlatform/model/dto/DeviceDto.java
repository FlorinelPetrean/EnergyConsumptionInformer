package com.ds.EnergyUtilityPlatform.model.dto;


import com.ds.EnergyUtilityPlatform.model.entity.Device;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto implements IDto<Device>{
    private Long id;

    private String description;

    private String address;

    private Long maxEnergyConsumption;

    private Float avgEnergyConsumption;

    public DeviceDto toDto(Device device) {
        return DeviceDto.builder()
                .id(device.getId())
                .description(device.getDescription())
                .address(device.getAddress())
                .maxEnergyConsumption(device.getMaxEnergyConsumption())
                .avgEnergyConsumption(device.getAvgEnergyConsumption())
                .build();
    }
}
