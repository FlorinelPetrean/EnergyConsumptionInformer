package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.SensorRepository;
import com.ds.EnergyUtilityPlatform.utils.BeanUtil;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService extends CrudService<Sensor> {
    private final SensorRepository sensorRepository;
    private final DeviceService deviceService;
    public SensorService(CrudRepository<Sensor> crudRepository, BeanUtil<Sensor> beanUtil, DeviceService deviceService) {
        super(crudRepository, beanUtil);
        this.sensorRepository = (SensorRepository) crudRepository;
        this.deviceService = deviceService;
    }

    @Transactional
    public List<Record> getSensorRecords(Long sensorId) {
        Sensor sensor = super.findById(sensorId);
        Hibernate.initialize(sensor.getRecords());
        return sensor.getRecords();
    }

    @Override
    public Sensor create(Sensor bean) {
        Long deviceId = bean.getDevice().getId();
        bean.setDevice(null);
        if (deviceId != null) {
            Device device = deviceService.findById(deviceId);
            bean.setDevice(device);

            super.create(bean);
            device.setSensor(bean);
            deviceService.save(device);

            return bean;
        }
        else {
            return super.create(bean);
        }
    }

    @Override
    public Sensor modify(Long id, Sensor bean) {
        bean.setRecords(null);
        bean.setDevice(null);
        return super.modify(id, bean);
    }

    //    @Override
//    public void deleteById(Long id) {
//        Sensor sensor = findById(id)
//        super.deleteById(id);
//    }

    public List<String> getTemplateSensors() {
        List<Sensor> all = sensorRepository.getSensorsByDeviceIsNull();
        return all.stream().map(Sensor::getDescription).collect(Collectors.toList());
    }
}
