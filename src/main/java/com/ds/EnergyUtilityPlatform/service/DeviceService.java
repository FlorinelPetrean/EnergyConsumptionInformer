package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService extends CrudService<Device> {
    public DeviceService(CrudRepository<Device> crudRepository) {
        super(crudRepository);
    }
}
