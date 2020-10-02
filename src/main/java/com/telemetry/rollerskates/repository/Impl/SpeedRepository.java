package com.telemetry.rollerskates.repository.Impl;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Speed;
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
public class SpeedRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpeedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(BaseDetector baseDetector) {
        Speed speed = (Speed)baseDetector;
        jdbcTemplate.update("insert into detectors.speed (value, measure, date_time) values (?, ?, ?)",
                speed.getSpeed(), speed.getMeasure(), LocalDateTime.now());
    }

    public List<BaseDetector> getMeasure(String start, String end) {
        String query = "SELECT * FROM detectors.speed WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<BaseDetector> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Speed speed = new Speed();
            speed.setSpeed(resultSet.getFloat("value"));
            speed.setMeasure(resultSet.getString("measure"));
            speed.setTimestamp(resultSet.getTimestamp("date_time").toLocalDateTime());
            return speed;
        })));
        return result;
    }
}
