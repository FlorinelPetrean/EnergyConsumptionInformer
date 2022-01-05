package com.ds.EnergyUtilityPlatform.rmi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class AppUserRmi implements Serializable {

    private String username;

    private String firstName;

    private String lastName;

    private String address;

    private Date dateOfBirth;

    private String role;

    private List<DeviceRmi> devices;

}
