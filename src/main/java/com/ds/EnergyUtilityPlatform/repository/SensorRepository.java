package com.ds.EnergyUtilityPlatform.repository;

import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>, CrudRepository<Sensor> {
    List<Sensor> getSensorsByDeviceIsNull();
}
