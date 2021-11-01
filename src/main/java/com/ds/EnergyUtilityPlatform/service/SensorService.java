package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService extends CrudService<Sensor> {
    private final SensorRepository sensorRepository;
    public SensorService(CrudRepository<Sensor> crudRepository) {
        super(crudRepository);
        this.sensorRepository = (SensorRepository) crudRepository;
    }

    public List<Record> getSensorRecords(Long sensorId) {
        Sensor sensor = super.findById(sensorId);
        return sensor.getRecords();
    }

    @Override
    public Sensor create(Sensor bean) {

        return super.create(bean);
    }

    public List<String> getTemplateSensors() {
        List<Sensor> all = sensorRepository.getSensorsByDeviceIsNull();
        return all.stream().map(Sensor::getDescription).collect(Collectors.toList());
    }
}
