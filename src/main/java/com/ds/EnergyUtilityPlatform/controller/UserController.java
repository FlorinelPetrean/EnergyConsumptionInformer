package com.ds.EnergyUtilityPlatform.controller;

import com.ds.EnergyUtilityPlatform.model.dto.*;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.service.UserService;
import com.ds.EnergyUtilityPlatform.utils.JwtUtility;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/u/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        AppUser user = userService.findByUsername(username);
        return (UserDto) dtoMapper.getDto(user);
    }

    @GetMapping(path = "/{username}/devices")
    public List<DeviceDto> getUserDevices(@PathVariable String username) {
        List<Device> all = userService.getUserDevices(username);
        return all.stream().map(device -> (DeviceDto) dtoMapper.getDto(device)).collect(Collectors.toList());
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
