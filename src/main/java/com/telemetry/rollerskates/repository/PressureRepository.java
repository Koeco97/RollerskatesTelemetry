package com.telemetry.rollerskates.repository;

import com.telemetry.rollerskates.entity.Pressure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PressureRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PressureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Pressure pressure) {
        jdbcTemplate.update("insert into detectors.pressure (value, measure, date_time) values (?, ?, ?)",
                pressure.getPressure(), pressure.getMeasure(), LocalDateTime.now());
    }

    public List<Pressure> getPressure(String start, String end) {
        String query = "SELECT * FROM detectors.pressure WHERE date_time " +
                "BETWEEN '" + start + "' AND '" + end + "'";
        List<Pressure> result = new ArrayList<>();
        result.addAll(jdbcTemplate.query(query, new Object[]{}, ((resultSet, i) -> {
            Pressure pressure = new Pressure();
            pressure.setPressure(resultSet.getFloat("value"));
            pressure.setMeasure(resultSet.getString("measure"));
            return pressure;
        })));
        return result;
    }
}
