package com.ds.EnergyUtilityPlatform.model.dto;

import com.ds.EnergyUtilityPlatform.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DtoMapper {

    private final HashMap<Class, Class> _handlerMap = new HashMap<>();
//    @Autowired
    private ApplicationContext context;

    public DtoMapper(ApplicationContext context) {
        this.context = context;
        _handlerMap.put(UserDto.class, AppUser.class);
        _handlerMap.put(SensorDto.class, Sensor.class);
        _handlerMap.put(DeviceDto.class, Device.class);
        _handlerMap.put(AppUser.class, UserDto.class);
        _handlerMap.put(Sensor.class, SensorDto.class);
        _handlerMap.put(Device.class, DeviceDto.class);
        _handlerMap.put(Record.class, RecordDto.class);
        _handlerMap.put(RecordDto.class, Record.class);
    }


    public <T> T getEntity(IDto<T> beanDTO) {
        Class handlerType = _handlerMap.get(beanDTO.getClass());
        try {
            IEntity<T> handler = (IEntity<T>) context.getBean(handlerType);
            return handler.toEntity(beanDTO);
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return null;
    }


    public <T> IDto<T> getDto(T entityBean) {
        Class handlerType = _handlerMap.get(entityBean.getClass());
        try {
            IDto<T> handler = (IDto<T>) context.getBean(handlerType);
            return handler.toDto(entityBean);
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
