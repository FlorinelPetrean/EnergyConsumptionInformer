package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.JwtResponse;
import com.ds.EnergyUtilityPlatform.model.dto.UserDetails;
import com.ds.EnergyUtilityPlatform.model.dto.UserDto;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.UserService;
import com.ds.EnergyUtilityPlatform.utils.JwtUtility;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
public class UserController extends CrudController<AppUser, UserDto> {
    private final UserService userService;
    private final JwtUtility jwtUtility;

    public UserController(ICrudService<AppUser> service, DtoMapper dtoMapper, JwtUtility jwtUtility) {
        super(service, dtoMapper);
        this.userService = (UserService) service;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody UserDetails userDetails) throws Exception {
        try {
            this.userService.login(userDetails.getUsername(), userDetails.getPassword());
        }
        catch (Exception e) {
            throw new Exception("Invalid Credentials", e);
        }

        String token = jwtUtility.generateToken(userDetails);
        return new JwtResponse(token);
    }


}
