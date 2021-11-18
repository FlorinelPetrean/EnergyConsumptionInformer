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

    private Double maxEnergyConsumption;

    private Double avgEnergyConsumption;

    private String username;

    private Long sensorId;

    public DeviceDto toDto(Device device) {
        String username = null;
        if (device.getUser() != null)
            username = device.getUser().getUsername();

        Long sensorId = null;
        if(device.getSensor() != null)
            sensorId = device.getSensor().getId();

        return DeviceDto.builder()
                .id(device.getId())
                .description(device.getDescription())
                .address(device.getAddress())
                .maxEnergyConsumption(device.getMaxEnergyConsumption())
                .avgEnergyConsumption(device.getAvgEnergyConsumption())
                .username(username)
                .sensorId(sensorId)
                .build();
    }
}
