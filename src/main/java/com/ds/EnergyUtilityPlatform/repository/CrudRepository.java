package com.ds.EnergyUtilityPlatform.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T bean);

    void deleteById(Long id);
}
