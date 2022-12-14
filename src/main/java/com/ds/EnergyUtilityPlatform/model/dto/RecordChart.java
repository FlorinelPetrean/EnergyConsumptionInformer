package com.ds.EnergyUtilityPlatform.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class RecordChart implements Serializable {
    private String timestamp;
    private Double energyConsumption;
}
