package com.ds.EnergyUtilityPlatform.service;


import com.ds.EnergyUtilityPlatform.model.dto.UserDetails;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.UserRepository;
import com.ds.EnergyUtilityPlatform.utils.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService extends CrudService<AppUser> {
    public UserRepository userRepository;

    public UserService(CrudRepository<AppUser> crudRepository) {
        super(crudRepository);
        this.userRepository = (UserRepository) crudRepository;
    }

    public AppUser findByUsername(String username) {
        Optional<AppUser> optionalUser = userRepository.findAppUserByUsername(username);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new IllegalStateException("User with username=" + username + " does not exist");
    }

    @Override
    public AppUser create(AppUser bean) {
        bean.setDevices(new ArrayList<>(50));
        String password = bean.getPassword();
        bean.setPassword(PasswordEncoder.encodePassword(password));
        return super.create(bean);
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
