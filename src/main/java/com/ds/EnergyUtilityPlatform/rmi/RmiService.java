package com.ds.EnergyUtilityPlatform.rmi;

import com.ds.EnergyUtilityPlatform.model.dto.RecordChart;
import com.ds.EnergyUtilityPlatform.rmi.model.AppUserRmi;
import com.ds.EnergyUtilityPlatform.rmi.model.ChartRecords;

import java.util.List;

public interface RmiService {
    public String sendMessage(String msg);
    public AppUserRmi login(String username, String password);
    public ChartRecords getDeviceHourlyEnergyConsumptionOverDays(Long deviceId, Long days, Long hour);
    public Double getAverageEnergyConsumptionOverWeek(Long deviceId, Long hour);
    public ChartRecords getProgramChart(Long deviceId, Long programHours);
}
