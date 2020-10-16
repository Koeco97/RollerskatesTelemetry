package com.telemetry.rollerskates.controller;

import com.telemetry.rollerskates.entity.Humidity;
import com.telemetry.rollerskates.entity.Pressure;
import com.telemetry.rollerskates.entity.Speed;
import com.telemetry.rollerskates.entity.Temperature;
import com.telemetry.rollerskates.repository.impl.MeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RollerSkatesControllerTest {
    @InjectMocks
    RollerSkatesController rollerSkatesController;
    @Mock
    MeasureRepository measureRepository;
    @Mock
    Model model;
    @Mock
    ModelMap modelMap;

    @Test
    void index() {
        assertEquals("index", rollerSkatesController.index(model));
    }

    @Test
    void submitForm_return_redirect() {
        ChartForm chartForm = new ChartForm();
        chartForm.start = "2020-10-07";
        chartForm.end = "2020-10-08";
        String redirect = "redirect:/chart?start=" + chartForm.getStart() + " 00:00" + "&end=" + chartForm.end + " 23:59";
        assertEquals(redirect, rollerSkatesController.submitForm(chartForm));
    }

    @Test
    void submitForm_IfEmptyChart_return_index() {
        ChartForm chartForm = new ChartForm();
        chartForm.start = "";
        chartForm.end = "";
        assertEquals("index", rollerSkatesController.submitForm(chartForm));
    }

    @Test
    void getAllCharts_return_charts() {
        String start = "2020-10-07";
        String end = "2020-10-08";
        Temperature temperature = new Temperature();
        temperature.setTimestamp(LocalDateTime.now());
        temperature.setMeasure("Celcius");
        temperature.setTemperature(30.0f);
        List temperatureList = List.of(temperature);
        Mockito.when(measureRepository.getMeasure(start, end, Temperature.class)).thenReturn(temperatureList);
        Humidity humidity = new Humidity();
        humidity.setTimestamp(LocalDateTime.now());
        humidity.setMeasure("percent");
        humidity.setHumidity(70.5f);
        List humidityList = List.of(humidity);
        Mockito.when(measureRepository.getMeasure(start, end, Humidity.class)).thenReturn(humidityList);
        Pressure pressure = new Pressure();
        pressure.setTimestamp(LocalDateTime.now());
        pressure.setMeasure("pascal");
        pressure.setPressure(2.1f);
        List pressureList = List.of(pressure);
        Mockito.when(measureRepository.getMeasure(start, end, Pressure.class)).thenReturn(pressureList);
        Speed speed = new Speed();
        speed.setTimestamp(LocalDateTime.now());
        speed.setMeasure("meters per second");
        speed.setSpeed(14.3f);
        List speedList = List.of(speed);
        Mockito.when(measureRepository.getMeasure(start, end, Speed.class)).thenReturn(speedList);
        assertEquals("charts", rollerSkatesController.getAllCharts(start, end, modelMap));
    }

    @Test
    void getChart_return_ModelMap() {
        String start = "2020-10-07";
        String end = "2020-10-08";
        Temperature temperature = new Temperature();
        temperature.setTimestamp(LocalDateTime.now());
        temperature.setMeasure("Celcius");
        temperature.setTemperature(30.0f);
        List temperatureList = List.of(temperature);
        Mockito.when(measureRepository.getMeasure(start, end, Temperature.class)).thenReturn(temperatureList);
        ModelMap result;
        Method[] methods = RollerSkatesController.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().contains("getChart")) {
                try {
                    method.setAccessible(true);
                    result = (ModelMap) method.invoke(rollerSkatesController, start, end, "Temperature");
                    assertEquals("Celcius", result.getAttribute("TemperatureMeasure"));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}