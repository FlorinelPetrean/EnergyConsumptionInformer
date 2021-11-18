package com.ds.EnergyUtilityPlatform.service.rabbitmq;

import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.RecordDto;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RabbitMQReceiver {
    private final DtoMapper dtoMapper;
    private final RecordService recordService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveData(RecordDto recordDto) {
        System.out.println("Message received: <" + recordDto.toString() + ">");
        Record record = dtoMapper.getEntity(recordDto);
        recordService.create(record);
    }

}


