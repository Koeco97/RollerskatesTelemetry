package com.telemetry.rollerskates.repository.Impl;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Temperature;
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
public class TemperatureRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TemperatureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(BaseDetector baseDetector) {
        Temperature temperature = (Temperature)baseDetector;
        jdbcTemplate.update("insert into detectors.temperature (value, measure, date_time) values (?, ?, ?)",
                temperature.getTemperature(), temperature.getMeasure(), LocalDateTime.now());
    }

    public List<BaseDetector> getMeasure(String start, String end) {
        String query = "SELECT * FROM detectors.temperature WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<BaseDetector> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Temperature temperature = new Temperature();
            temperature.setTemperature(resultSet.getFloat("value"));
            temperature.setMeasure(resultSet.getString("measure"));
            temperature.setTimestamp(resultSet.getTimestamp("date_time").toLocalDateTime());
            return temperature;
        })));
        return result;
    }
}
