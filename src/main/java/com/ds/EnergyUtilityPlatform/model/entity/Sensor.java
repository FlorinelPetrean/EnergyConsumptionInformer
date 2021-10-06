package com.ds.EnergyUtilityPlatform.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Sensor {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String description;

    @Column
    private Long maxValue;

}
