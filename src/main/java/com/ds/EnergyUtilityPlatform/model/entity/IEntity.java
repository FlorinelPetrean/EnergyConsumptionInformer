package com.ds.EnergyUtilityPlatform.model.entity;

import com.ds.EnergyUtilityPlatform.model.dto.entitydto.IDto;

public interface IEntity<T> {
    T toEntity(IDto<T> dto);
}
