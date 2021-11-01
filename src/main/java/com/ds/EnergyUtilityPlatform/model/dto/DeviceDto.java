package com.ds.EnergyUtilityPlatform.model.dto;


import com.ds.EnergyUtilityPlatform.model.entity.Device;
import lombok.*;
import org.springframework.stereotype.Component;


@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto implements IDto<Device>{
    private Long id;

    private String description;

    private String address;

    private Long maxEnergyConsumption;

    private Float avgEnergyConsumption;

    private String username;

    public DeviceDto toDto(Device device) {
        String username = null;
        if (device.getUser() != null)
            username = device.getUser().getUsername();
        return DeviceDto.builder()
                .id(device.getId())
                .description(device.getDescription())
                .address(device.getAddress())
                .maxEnergyConsumption(device.getMaxEnergyConsumption())
                .avgEnergyConsumption(device.getAvgEnergyConsumption())
                .username(username)
                .build();
    }
}
