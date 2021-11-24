package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.entitydto.RecordDto;
import com.ds.EnergyUtilityPlatform.model.dto.entitydto.SensorDto;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.SensorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/sensor")
public class SensorController extends CrudController<Sensor, SensorDto> {
    private final SensorService sensorService;

    public SensorController(ICrudService<Sensor> service, DtoMapper dtoMapper) {
        super(service, dtoMapper);
        this.sensorService = (SensorService) service;
    }

    @GetMapping(path = "/templates")
    public List<String> getTemplateSensors() {
        return sensorService.getTemplateSensors();
    }

    @GetMapping(path = "/{sensorId}/records")
    public List<RecordDto> getSensorRecords(@PathVariable Long sensorId) {
        List<Record> all = sensorService.getSensorRecords(sensorId);
        return all.stream().map(record -> (RecordDto) dtoMapper.getDto(record)).collect(Collectors.toList());
    }
}
