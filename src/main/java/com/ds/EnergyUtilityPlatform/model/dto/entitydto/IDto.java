package com.ds.EnergyUtilityPlatform.model.dto.entitydto;

public interface IDto<T> {
    IDto<T> toDto(T entity);
}
