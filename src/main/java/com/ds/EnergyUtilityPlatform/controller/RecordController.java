package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.RecordDto;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.RecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/record")
public class RecordController extends CrudController<Record, RecordDto> {
    private final RecordService recordService;
    public RecordController(ICrudService<Record> service, DtoMapper dtoMapper) {
        super(service, dtoMapper);
        this.recordService = (RecordService) service;
    }

    @GetMapping(path = "/list/{sensorId}")
    public List<IDto<Record>> getSensorRecords(@PathVariable Long sensorId) {
        List<Record> all = recordService.getSensorRecords(sensorId);
        return all.stream().map(dtoMapper::getDto).collect(Collectors.toList());
    }
}
