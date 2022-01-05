package com.ds.EnergyUtilityPlatform.rmi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class DeviceRmi implements Serializable {
    private Long id;

    private String description;

    private String address;

    private Double maxEnergyConsumption;

    private Double avgEnergyConsumption;

}
