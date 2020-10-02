package com.telemetry.rollerskates.repository.Impl;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Pressure;
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
public class PressureRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PressureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public void save(BaseDetector baseDetector) {
        Pressure pressure = (Pressure)baseDetector;
        jdbcTemplate.update("insert into detectors.pressure (value, measure, date_time) values (?, ?, ?)",
                pressure.getPressure(), pressure.getMeasure(), LocalDateTime.now());
    }

    public List<BaseDetector> getMeasure(String start, String end) {
        String query = "SELECT * FROM detectors.pressure WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<BaseDetector> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Pressure pressure = new Pressure();
            pressure.setPressure(resultSet.getFloat("value"));
            pressure.setMeasure(resultSet.getString("measure"));
            pressure.setTimestamp(resultSet.getTimestamp("date_time").toLocalDateTime());
            return pressure;
        })));
        return result;
    }
}
