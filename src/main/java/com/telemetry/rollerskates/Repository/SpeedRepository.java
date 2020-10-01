package com.telemetry.rollerskates.Repository;

import com.telemetry.rollerskates.entity.Speed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpeedRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpeedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Speed speed) {
        jdbcTemplate.update("insert into detectors.speed (value, measure, date_time) values (?, ?, ?)",
                speed.getSpeed(), speed.getMeasure(), LocalDateTime.now());
    }

    public List<Speed> getSpeed(String start, String end) {
        String query = "SELECT * FROM detectors.speed WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<Speed> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Speed speed = new Speed();
            speed.setSpeed(resultSet.getFloat("value"));
            speed.setMeasure(resultSet.getString("measure"));
            return speed;
        })));
        return result;
    }
}
