package com.ds.EnergyUtilityPlatform.rmi;

import com.ds.EnergyUtilityPlatform.model.dto.RecordChart;
import com.ds.EnergyUtilityPlatform.model.entity.AppUser;

import java.util.List;

public interface RmiService {
    public String sendMessage(String msg);
    public AppUser login(String username, String password);
    public List<RecordChart> getDeviceHourlyEnergyConsumptionOverDays(Long deviceId, Long days, Long hour);
    public Double getAverageEnergyConsumptionOverWeek(Long deviceId, Long hour);
}
