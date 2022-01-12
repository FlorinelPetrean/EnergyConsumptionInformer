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
    public List<RecordChart> records;
}
