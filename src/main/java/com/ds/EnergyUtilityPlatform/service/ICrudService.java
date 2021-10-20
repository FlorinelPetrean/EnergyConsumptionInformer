package com.ds.EnergyUtilityPlatform.service;

import java.util.List;

public interface ICrudService<T> {

    List<T> findAll();

    T findById(Long id);

    T create(T bean);

    T modify(T bean);

    void deleteById(Long id);
}
