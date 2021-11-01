package com.ds.EnergyUtilityPlatform.repository;

import com.ds.EnergyUtilityPlatform.model.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, CrudRepository<Record> {
    List<Record> getRecordsBySensorId(Long sensorId);
}
