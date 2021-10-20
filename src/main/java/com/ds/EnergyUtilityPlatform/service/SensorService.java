package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorService extends CrudService<Sensor> {
    public SensorService(CrudRepository<Sensor> crudRepository) {
        super(crudRepository);
    }
}
