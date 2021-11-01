package com.ds.EnergyUtilityPlatform.repository;

import com.ds.EnergyUtilityPlatform.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, CrudRepository<Device> {
    List<Device> getDevicesByUserIsNull();
}
