package com.ds.EnergyUtilityPlatform.rmi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class ChartRecords implements Serializable {
    private List<RecordChart> records;
}
