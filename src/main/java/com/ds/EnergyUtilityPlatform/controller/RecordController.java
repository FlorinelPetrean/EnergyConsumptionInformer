package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.RecordChart;
import com.ds.EnergyUtilityPlatform.model.dto.entitydto.RecordDto;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/record")
public class RecordController extends CrudController<Record, RecordDto> {
    private final RecordService recordService;
    public RecordController(ICrudService<Record> service, DtoMapper dtoMapper) {
        super(service, dtoMapper);
        this.recordService = (RecordService) service;
    }

    @GetMapping(path = "/list/{sensorId}/{date}")
    public List<RecordChart> getSensorRecords(@PathVariable Long sensorId, @PathVariable String date) {
        return recordService.getSensorRecordsByDay(sensorId, date);
    }

    @Override
    public ResponseEntity<Void> create(RecordDto bean) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
