package com.ds.EnergyUtilityPlatform.repository;

import com.ds.EnergyUtilityPlatform.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, CrudRepository<Device> {
}
