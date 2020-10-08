package com.telemetry.rollerskates.controller;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.repository.impl.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RollerSkatesRestController {
    @Autowired
    private MeasureRepository measureRepository;
    private static final String ENTITY_PACKAGE = "com.telemetry.rollerskates.entity.";


    @GetMapping(value = "/{detectorType}")
    public List<BaseDetector> getMeasure(@PathVariable("detectorType") String detectorType, @RequestParam(required = false) String start,
                                         @RequestParam(required = false) String end) {
        List<BaseDetector> result = new ArrayList<>();
        try {
            result = measureRepository.getMeasure(start, end, Class.forName(ENTITY_PACKAGE + detectorType));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
