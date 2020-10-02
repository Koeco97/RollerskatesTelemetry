package com.telemetry.rollerskates.repository.Impl;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Humidity;
import com.telemetry.rollerskates.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HumidityRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HumidityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(BaseDetector baseDetector) {
        Humidity humidity = (Humidity)baseDetector;
        jdbcTemplate.update("insert into detectors.humidity (value, measure, date_time) values (?, ?, ?)",
                humidity.getHumidity(), humidity.getMeasure(), LocalDateTime.now());
    }

    public List<BaseDetector> getMeasure(String start, String end) {
        String query = "SELECT * FROM detectors.humidity WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<BaseDetector> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Humidity humidity = new Humidity();
            humidity.setHumidity(resultSet.getFloat("value"));
            humidity.setMeasure(resultSet.getString("measure"));
            humidity.setTimestamp(resultSet.getTimestamp("date_time").toLocalDateTime());
            return humidity;
        })));
        return result;
    }
}
