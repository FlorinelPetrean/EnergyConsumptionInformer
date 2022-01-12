package com.ds.EnergyUtilityPlatform.rmi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class RecordChart implements Serializable {
    public String timestamp;
    public Double energyConsumption;
}
