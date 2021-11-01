package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DeviceService extends CrudService<Device> {
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    public DeviceService(CrudRepository<Device> crudRepository, UserService userService) {
        super(crudRepository);
        this.deviceRepository = (DeviceRepository) crudRepository;
        this.userService = userService;
    }

    @Override
    public Device create(Device bean) {
        String username = bean.getUser().getUsername();
        bean.setUser(null);
        if (username != null && !username.equals("")) {
            AppUser user = userService.findByUsername(username);
            bean.setUser(user);
            bean.setAddress(user.getAddress());
            bean.setAvgEnergyConsumption(0.0f);
            bean.setMaxEnergyConsumption(0L);
        }
        return super.create(bean);
    }

    public List<String> getTemplateDevices() {
        List<Device> all = deviceRepository.getDevicesByUserIsNull();
        return all.stream().map(Device::getDescription).collect(Collectors.toList());
    }


}
