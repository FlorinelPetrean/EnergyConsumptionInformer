package com.ds.EnergyUtilityPlatform.service;


import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.UserRepository;
import com.ds.EnergyUtilityPlatform.utils.PasswordEncoder;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends CrudService<AppUser> {
    private final UserRepository userRepository;
    private final DeviceService deviceService;
    public UserService(CrudRepository<AppUser> crudRepository, @Lazy DeviceService deviceService) {
        super(crudRepository);
        this.userRepository = (UserRepository) crudRepository;
        this.deviceService = deviceService;
    }

    @Transactional
    public AppUser findByUsername(String username) {
        Optional<AppUser> optionalUser = userRepository.findAppUserByUsername(username);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new IllegalStateException("User with username=" + username + " does not exist");
    }

    @Transactional
    public List<Device> getUserDevices(String username) {
        AppUser user = findByUsername(username);
        Hibernate.initialize(user.getDevices());
        return user.getDevices();
    }

    @Transactional
    public Float getEnergyConsumption(String username) {
        List<Device> devices = getUserDevices(username);
        Float sum = 0.0f;
        for(Device device : devices) {
            sum += device.getAvgEnergyConsumption();
        }
        return sum;
    }


    @Override
    public AppUser create(AppUser bean) {
        bean.setDevices(new ArrayList<>(50));
        String password = bean.getPassword();
        bean.setPassword(PasswordEncoder.encodePassword(password));
        return super.create(bean);
    }

    @Override
    public AppUser modify(AppUser bean) {
        AppUser user = findByUsername(bean.getUsername());
        bean.setPassword(user.getPassword());
        return super.modify(bean);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        AppUser user = findById(id);
        Hibernate.initialize(user.getDevices());
        List<Device> devices = user.getDevices();
        if (!devices.isEmpty()) {
            for (Device device : devices) {
                deviceService.deleteById(device.getId());
            }
        }
        super.deleteById(id);
    }

    public AppUser login(String username, String password) {
        AppUser user = findByUsername(username);
        String encryptedPassword = user.getPassword();
        if(PasswordEncoder.checkPassword(password, encryptedPassword)) {
            return user;
        }
        throw new IllegalStateException("Incorrect credentials");
    }
}
