package com.telemetry.rollerskates;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Temperature;
import com.telemetry.rollerskates.repository.Impl.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private TemperatureRepository temperatureRepository;


    @GetMapping(value = "/{temperature}")
    public List<BaseDetector> getTemperature(@RequestParam(required = false) String start,
                                             @RequestParam(required = false) String end) {
        return temperatureRepository.getMeasure(start, end);
    }

    @GetMapping(value = "/")
    public String getHome() {
        return "index";
    }
}
