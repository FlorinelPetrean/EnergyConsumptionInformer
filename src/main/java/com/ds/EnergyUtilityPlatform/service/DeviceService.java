package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.DeviceRepository;
import com.ds.EnergyUtilityPlatform.utils.BeanUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService extends CrudService<Device> {
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final SensorService sensorService;
    public DeviceService(CrudRepository<Device> crudRepository, BeanUtil<Device> beanUtil, UserService userService, SensorService sensorService) {
        super(crudRepository, beanUtil);
        this.deviceRepository = (DeviceRepository) crudRepository;
        this.userService = userService;
        this.sensorService = sensorService;
    }

    @Override
    public Device create(Device bean) {
        String username = bean.getUser().getUsername();
        bean.setUser(null);
        bean.setSensor(null);
        if (username != null && !username.equals("")) {
            AppUser user = userService.findByUsername(username);
            bean.setUser(user);
            bean.setAddress(user.getAddress());
            bean.setAvgEnergyConsumption(0.0);
            bean.setMaxEnergyConsumption(0.0);
        }
        return super.create(bean);
    }


    @Override
    public Device modify(Long id, Device bean) {
        bean.setSensor(null);
        bean.setUser(null);
        return super.modify(id, bean);
    }

    @Override
    public void deleteById(Long id) {
        Device device = findById(id);
        Sensor sensor = device.getSensor();
        if (sensor != null)
            sensorService.deleteById(sensor.getId());
        super.deleteById(id);
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public List<String> getTemplateDevices() {
        List<Device> all = deviceRepository.getDevicesByUserIsNull();
        return all.stream().map(Device::getDescription).collect(Collectors.toList());
    }


}
