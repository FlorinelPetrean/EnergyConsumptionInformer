package com.ds.EnergyUtilityPlatform.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RecordChart {
    private String timestamp;
    private Long energyConsumption;
}
