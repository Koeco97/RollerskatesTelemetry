package com.telemetry.rollerskates.controller;

import com.telemetry.rollerskates.entity.Temperature;
import com.telemetry.rollerskates.repository.impl.MeasureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RollerSkatesRestControllerTest {
    @InjectMocks
    RollerSkatesRestController rollerSkatesRestController;
    @Mock
    MeasureRepository measureRepository;

    @Test
    public void getMeasure() {
        Temperature temperature = new Temperature();
        List detectorList = List.of(temperature);
        Mockito.when(measureRepository.getMeasure("2020-10-07", "2020-10-08", Temperature.class)).thenReturn(detectorList);
        List result = rollerSkatesRestController.getMeasure("Temperature", "2020-10-07", "2020-10-08");
        assertEquals(detectorList, result);
    }
}