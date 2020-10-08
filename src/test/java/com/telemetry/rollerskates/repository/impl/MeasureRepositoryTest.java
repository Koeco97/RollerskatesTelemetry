package com.telemetry.rollerskates.repository.impl;

import com.telemetry.rollerskates.entity.Pressure;
import com.telemetry.rollerskates.entity.Temperature;
import junit.runner.Version;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.List;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MeasureRepositoryTest {
    @InjectMocks
    MeasureRepository measureRepository;
    @Mock
    JdbcTemplate jdbcTemplate;

    @Test
    void getMeasure_return_Temperature_class() {
        Temperature temperature = new Temperature();
        Mockito.when(this.jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<Object[]>any(),
                ArgumentMatchers.<RowMapper<Temperature>>any())).thenReturn(List.of(temperature));
        List temperatures = measureRepository.getMeasure("2020-09-29", "2020-10-03", Temperature.class);
        assertEquals(Temperature.class, temperatures.get(0).getClass());
    }

    @Test
    void getMeasure_return_Pressure_class() {
        Pressure pressure = new Pressure();
        Mockito.when(this.jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<Object[]>any(),
                ArgumentMatchers.<RowMapper<Pressure>>any())).thenReturn(List.of(pressure));
        List pressures = measureRepository.getMeasure("2020-09-29", "2020-10-03", Pressure.class);
        assertEquals(Pressure.class, pressures.get(0).getClass());
    }


    @Test
    void save() {
        Temperature temperature = new Temperature();
        temperature.setTemperature(30f);
        temperature.setMeasure("Celsius");
        measureRepository.save(temperature);
        Mockito.verify(jdbcTemplate).update("insert into detectors.temperature (value, measure, date_time) values (?, ?, ?)",
                30f, "Celsius", LocalDate.now());
    }
}