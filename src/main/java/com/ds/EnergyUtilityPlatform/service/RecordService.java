package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RecordService extends CrudService<Record> implements ICrudService<Record>{
    private final RecordRepository recordRepository;
    private final SensorService sensorService;
    public RecordService(CrudRepository<Record> crudRepository, SensorService sensorService) {
        super(crudRepository);
        this.recordRepository = (RecordRepository) crudRepository;
        this.sensorService = sensorService;
    }

    @Override
    public Record create(Record bean) {
        Long sensorId = bean.getSensor().getId();
        bean.setSensor(null);
        if (sensorId != null) {
            Sensor sensor = sensorService.findById(sensorId);
            bean.setSensor(sensor);
            bean.setTimestamp(LocalDateTime.now());
        }
        return super.create(bean);
    }

    public List<Record> getSensorRecords(Long sensorId) {
        return recordRepository.getRecordsBySensorId(sensorId);
    }
}
