package com.telemetry.rollerskates.controller;

import com.telemetry.rollerskates.repository.impl.MeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        String redirect = "redirect:/chart?start=" + chartForm.getStart() + "&end=" + chartForm.end;
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
    void getAllCharts_return_chart() {
        String start = "2020-10-07";
        String end = "2020-10-08";
        assertEquals("chart", rollerSkatesController.getAllCharts(start, end, modelMap));
    }
}