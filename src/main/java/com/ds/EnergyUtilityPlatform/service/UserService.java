package com.ds.EnergyUtilityPlatform.service;


import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<AppUser> {
    public UserService(CrudRepository<AppUser> crudRepository) {
        super(crudRepository);
    }
}
