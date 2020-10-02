package com.telemetry.rollerskates.repository.Impl;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Humidity;
import com.telemetry.rollerskates.entity.Pressure;
import com.telemetry.rollerskates.entity.Speed;
import com.telemetry.rollerskates.entity.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Repository
public class MeasureRepository <T extends BaseDetector>{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeasureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void save(T detector) {
        String name = detector.getClass().getSimpleName().toLowerCase();
        try {
            Field field = detector.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Float value = (Float)field.get(detector);
            jdbcTemplate.update("insert into detectors."+ name +" (value, measure, date_time) values (?, ?, ?)",
                    value, detector.getMeasure(), LocalDateTime.now());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
