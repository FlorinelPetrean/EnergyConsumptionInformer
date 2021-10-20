package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DeviceDto;
import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.service.DeviceService;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/device")
public class DeviceController extends CrudController<Device, DeviceDto> {
    private final DeviceService deviceService;

    public DeviceController(ICrudService<Device> service, DtoMapper dtoMapper) {
        super(service, dtoMapper);
        this.deviceService = (DeviceService) service;
    }
}
