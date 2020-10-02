package com.telemetry.rollerskates.entity;

import lombok.Data;

@Data
public class Temperature extends BaseDetector {
    private Float temperature;

    @Override
    public String toString() {
        return "Temperature{" +
                "Temperature=" + temperature +
                " " + super.toString() +
                '}';
    }
}
