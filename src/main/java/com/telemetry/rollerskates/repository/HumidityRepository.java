package com.telemetry.rollerskates.repository;

import com.telemetry.rollerskates.entity.Humidity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HumidityRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HumidityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Humidity humidity) {
        jdbcTemplate.update("insert into detectors.humidity (value, measure, date_time) values (?, ?, ?)",
                humidity.getHumidity(), humidity.getMeasure(), LocalDateTime.now());
    }

    public List<Humidity> getHumidity(String start, String end) {
        String query = "SELECT * FROM detectors.humidity WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<Humidity> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Humidity humidity = new Humidity();
            humidity.setHumidity(resultSet.getFloat("value"));
            humidity.setMeasure(resultSet.getString("measure"));
            return humidity;
        })));
        return result;
    }
}
