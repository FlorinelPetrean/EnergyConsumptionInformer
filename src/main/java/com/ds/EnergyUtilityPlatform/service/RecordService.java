package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.config.RabbitMQConfig;
import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.Notification;
import com.ds.EnergyUtilityPlatform.model.dto.RecordChart;
import com.ds.EnergyUtilityPlatform.model.dto.entitydto.RecordDto;
import com.ds.EnergyUtilityPlatform.model.entity.Device;
import com.ds.EnergyUtilityPlatform.model.entity.Record;
import com.ds.EnergyUtilityPlatform.model.entity.Sensor;
import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.repository.RecordRepository;
import com.ds.EnergyUtilityPlatform.repository.SensorRepository;
import com.ds.EnergyUtilityPlatform.utils.BeanUtil;
import org.hibernate.Hibernate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecordService extends CrudService<Record> {
    private final RecordRepository recordRepository;
    private final SensorService sensorService;
    private final DeviceService deviceService;
    private final DtoMapper dtoMapper;
    private final NotificationService notificationService;
    public RecordService(CrudRepository<Record> crudRepository, BeanUtil<Record> beanUtil, SensorService sensorService, SensorRepository sensorRepository, DeviceService deviceService, DtoMapper dtoMapper, NotificationService notificationService) {
        super(crudRepository, beanUtil);
        this.recordRepository = (RecordRepository) crudRepository;
        this.sensorService = sensorService;
        this.deviceService = deviceService;
        this.dtoMapper = dtoMapper;
        this.notificationService = notificationService;
    }

    @Override
    public Record create(Record bean) {
        Long sensorId = bean.getSensor().getId();
        bean.setSensor(null);
        if (sensorId != null) {
            Sensor sensor = sensorService.findById(sensorId);
            bean.setSensor(sensor);
        }
        super.create(bean);
        this.updateEnergyValues(bean);
        return bean;
    }

    @Transactional
    public void updateEnergyValues(Record record) {
        Sensor sensor = record.getSensor();
        Device device = sensor.getDevice();
        Double energy = record.getEnergyConsumption();
        if(device.getMaxEnergyConsumption() < energy) {
            device.setMaxEnergyConsumption(energy);
        }

        Hibernate.initialize(sensor.getRecords());
        List<Record> records = sensor.getRecords();
        double avg = 0.0;
        Double sum = 0.0;
        for(Record r : records) {
            sum += r.getEnergyConsumption();
        }
        if (records.size() != 0)
            avg = sum / records.size();
        device.setAvgEnergyConsumption(avg);

        deviceService.save(device);

    }



    public List<RecordChart> getSensorRecordsByDay(Long sensorId, String day) {
        List<Record> all = recordRepository.getRecordsBySensorId(sensorId);
        ZonedDateTime zdt = ZonedDateTime.now();
        return all.stream()
                .filter(record -> record.getTimestamp().toString().contains(day))
                .map(record ->
                        new RecordChart(Instant.ofEpochMilli(record.getTimestamp()).atZone(ZoneId.from(zdt)).toLocalDateTime().toLocalTime().toString(), record.getEnergyConsumption()))
                .collect(Collectors.toList());
    }


    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveRecord(RecordDto recordDto) {
        if (recordDto != null && sensorService.doesExist(recordDto.getSensorId())) {
            Long sensorId = recordDto.getSensorId();
            System.out.println("Message received: <" + recordDto + ">");
            Sensor sensor = sensorService.findById(sensorId);
            Record lastRecord = sensorService.getLatestRecord(sensor);
            if (lastRecord != null) {
                ZonedDateTime zdt = ZonedDateTime.now();
                Long t1 = recordDto.getTimestamp();
                Long t2 = lastRecord.getTimestamp();
                double peak = (recordDto.getEnergyConsumption() - lastRecord.getEnergyConsumption()) / (t1 - t2);
//                if (peak > sensor.getMaxValue()) {
                    System.out.println("Sending notification: <" + recordDto + ">");
                    Notification notification = new Notification("Energy Consumption too high:  " + peak + "\n");
                    String username = sensor.getDevice().getUser().getUsername();
                    notificationService.notify(notification, username);
//                }

            }
            Record currentRecord = dtoMapper.getEntity(recordDto);
            this.create(currentRecord);
        }
    }
}
