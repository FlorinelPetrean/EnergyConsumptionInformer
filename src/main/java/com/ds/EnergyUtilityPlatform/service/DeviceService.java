package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.DeviceRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService extends CrudService<Device> {
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final SensorService sensorService;
    public DeviceService(CrudRepository<Device> crudRepository, UserService userService, @Lazy SensorService sensorService) {
        super(crudRepository);
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
            bean.setAvgEnergyConsumption(0.0f);
            bean.setMaxEnergyConsumption(0L);
        }
        return super.create(bean);
    }

    @Override
    public Device modify(Device bean) {
        String username = bean.getUser().getUsername();
        Long sensorId = bean.getSensor().getId();
        bean.setUser(null);
        bean.setSensor(null);
        if (username != null && !username.equals("")) {
            AppUser user = userService.findByUsername(username);
            bean.setUser(user);
        }
        if(sensorId != null) {
            Sensor sensor = sensorService.findById(sensorId);
            bean.setSensor(sensor);
        }
        return super.modify(bean);
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public List<String> getTemplateDevices() {
        List<Device> all = deviceRepository.getDevicesByUserIsNull();
        return all.stream().map(Device::getDescription).collect(Collectors.toList());
    }


}
