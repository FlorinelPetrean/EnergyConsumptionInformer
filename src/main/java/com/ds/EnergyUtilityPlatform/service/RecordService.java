package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.RecordRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecordService extends CrudService<Record> implements ICrudService<Record>{
    private final RecordRepository recordRepository;
    private final SensorService sensorService;
    private final DeviceService deviceService;
    public RecordService(CrudRepository<Record> crudRepository, SensorService sensorService, DeviceService deviceService) {
        super(crudRepository);
        this.recordRepository = (RecordRepository) crudRepository;
        this.sensorService = sensorService;
        this.deviceService = deviceService;
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
        super.create(bean);
        updateEnergyValues(bean);
        return bean;
    }

    @Transactional
    public void updateEnergyValues(Record record) {
        Sensor sensor = record.getSensor();
        Device device = sensor.getDevice();
        Long energy = record.getEnergyConsumption();
        if (sensor.getMaxValue() < energy) {
            sensor.setMaxValue(energy);
        }
        if(device.getMaxEnergyConsumption() < energy) {
            device.setMaxEnergyConsumption(energy);
        }

        Hibernate.initialize(sensor.getRecords());
        List<Record> records = sensor.getRecords();
        Float avg = 0.0f;
        Long sum = 0L;
        for(Record r : records) {
            sum += r.getEnergyConsumption();
        }
        avg = (float) (sum / records.size());
        device.setAvgEnergyConsumption(avg);

        deviceService.save(device);

    }



    public List<Record> getSensorRecordsByDay(Long sensorId, String day) {
        List<Record> all = recordRepository.getRecordsBySensorId(sensorId);
        return all.stream().filter(record -> record.getTimestamp().toString().contains(day)).collect(Collectors.toList());
    }
}
