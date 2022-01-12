package com.ds.EnergyUtilityPlatform.rmi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class DeviceRmi implements Serializable {
    public Long id;

    public String description;

    public String address;

    public Double maxEnergyConsumption;

    public Double avgEnergyConsumption;

}
