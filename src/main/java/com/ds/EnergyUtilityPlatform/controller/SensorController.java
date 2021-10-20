package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.SensorDto;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.SensorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/sensor")
public class SensorController extends CrudController<Sensor, SensorDto> {
    private final SensorService sensorService;

    public SensorController(ICrudService<Sensor> service, DtoMapper dtoMapper) {
        super(service, dtoMapper);
        this.sensorService = (SensorService) service;
    }
}
