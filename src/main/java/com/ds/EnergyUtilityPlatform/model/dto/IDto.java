package com.ds.EnergyUtilityPlatform.model.dto;

import com.ds.EnergyUtilityPlatform.model.entity.IEntity;

public interface IDto<T> {
    IDto<T> toDto(T entity);
}
