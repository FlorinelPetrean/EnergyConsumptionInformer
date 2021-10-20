package com.ds.EnergyUtilityPlatform.model.entity;

import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.UserDto;

public interface IEntity<T> {
    T toEntity(IDto<T> dto);
}
