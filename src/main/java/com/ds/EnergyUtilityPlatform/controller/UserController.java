package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.UserDto;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.UserService;
import com.ds.EnergyUtilityPlatform.utils.JwtUtility;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController extends CrudController<AppUser, UserDto> {
    private final UserService userService;
    private final JwtUtility jwtUtility;

    public UserController(ICrudService<AppUser> service, DtoMapper dtoMapper, JwtUtility jwtUtility) {
        super(service, dtoMapper);
        this.userService = (UserService) service;
        this.jwtUtility = jwtUtility;
    }


}
