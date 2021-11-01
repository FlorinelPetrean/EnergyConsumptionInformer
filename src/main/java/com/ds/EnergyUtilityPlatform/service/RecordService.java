package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RecordService extends CrudService<Record> implements ICrudService<Record>{
    private final RecordRepository recordRepository;
    public RecordService(CrudRepository<Record> crudRepository) {
        super(crudRepository);
        this.recordRepository = (RecordRepository) crudRepository;
    }

    public List<Record> getSensorRecords(Long sensorId) {
        return recordRepository.getRecordsBySensorId(sensorId);
    }
}
