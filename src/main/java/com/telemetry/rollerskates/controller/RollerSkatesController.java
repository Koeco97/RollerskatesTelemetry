package com.telemetry.rollerskates.controller;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.repository.impl.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RollerSkatesController {
    @Autowired
    private MeasureRepository measureRepository;
    private static final String ENTITY_PACKAGE = "com.telemetry.rollerskates.entity.";
    private static final List<String> TYPES = List.of("Temperature", "Humidity", "Pressure", "Speed");

    @GetMapping(value = "/")
    public String getIndexPage(Model model) {
        model.addAttribute("chartForm", new ChartForm());
        return "index";
    }

    @PostMapping(value = "/")
    public String submitForm(@ModelAttribute ChartForm chartForm) {
        if (chartForm.start.equals("") || chartForm.end.equals("")) {
            return "index";
        }
        return "redirect:/chart?start=" + chartForm.getStart()+" 00:00" + "&end=" + chartForm.end+" 23:59";
    }

    @GetMapping(value = "/chart")
    public String getAllCharts(@RequestParam(required = false) String start,
                               @RequestParam(required = false) String end,
                               ModelMap modelMap) {
        for (String type : TYPES) {
            modelMap.addAllAttributes(getChart(start, end, type));
        }
        return "charts";
    }

    private ModelMap getChart(String start, String end, String type) {
        ModelMap modelMap = new ModelMap();
        List<BaseDetector> result = new ArrayList<>();
        try {
            result = measureRepository.getMeasure(start, end, Class.forName(ENTITY_PACKAGE + type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Map<Object, Object>> dataPoints = new ArrayList<Map<Object, Object>>();
        List<String> labels = new ArrayList<>();
        for (BaseDetector baseDetector : result) {
            Field field = null;
            try {
                field = baseDetector.getClass().getDeclaredField(type.toLowerCase());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            field.setAccessible(true);
            Float value = null;
            try {
                value = (Float) field.get(baseDetector);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Map<Object, Object> map = new HashMap<>();
            map.put("t", baseDetector.getTimestamp().atOffset(ZoneOffset.of("+03:00")).toInstant().toEpochMilli());
            map.put("y", value);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            labels.add(formatter.format(baseDetector.getTimestamp()));
            dataPoints.add(map);
        }
        modelMap.addAttribute(type + "DataPointsList", dataPoints);
        if (!result.isEmpty()) {
            modelMap.addAttribute(type + "Measure", result.get(0).getMeasure().trim());
        }
        else{
            modelMap.addAttribute(type + "Measure", "");
        }
        modelMap.addAttribute(type + "Labels", labels);
        return modelMap;
    }
}
