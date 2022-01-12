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

    public String username;

    public String firstName;

    public String lastName;

    public String address;

    public Date dateOfBirth;

    public String role;

    public List<DeviceRmi> devices;

}
