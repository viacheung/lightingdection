package com.nanjing.lightingdection.entity;
import java.io.Serializable;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class AirDevise {
    private Integer sensorId;
    private Date mesureTime;
    private Integer maxValue;
    private Integer avgValue;
    private Integer alarmLevel;
    private Integer lightningCount;
    private boolean onLine;

    public AirDevise() {
    }
}
