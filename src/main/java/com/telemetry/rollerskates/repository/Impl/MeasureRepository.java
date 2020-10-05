package com.telemetry.rollerskates.repository.Impl;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.joran.util.beans.BeanUtil.isSetter;

@Repository
public class MeasureRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeasureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(BaseDetector detector) {
        String name = detector.getClass().getSimpleName().toLowerCase();
        try {
            Field field = detector.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Float value = (Float) field.get(detector);
            jdbcTemplate.update("insert into detectors." + name + " (value, measure, date_time) values (?, ?, ?)",
                    value, detector.getMeasure(), LocalDateTime.now());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BaseDetector> getMeasure(String start, String end, Class type) {
        String query = "SELECT * FROM detectors." + type.getSimpleName().toLowerCase() + " WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<BaseDetector> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            BaseDetector detector = null;
            try {
                detector = (BaseDetector) type.getDeclaredConstructor().newInstance();
                Method[] methods = type.getMethods();
                for (Method method : methods) {
                    if (isSetter(method) && method.getName().contains(type.getSimpleName())) {
                        method.invoke(detector, resultSet.getFloat("value"));
                    }
                }
                detector.setMeasure(resultSet.getString("measure"));
                detector.setTimestamp(resultSet.getTimestamp("date_time").toLocalDateTime());
            } catch (InstantiationException | IllegalAccessException |
                    InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return detector;
        })));
        return result;
    }

}
