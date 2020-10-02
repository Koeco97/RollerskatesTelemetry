package com.telemetry.rollerskates.entity;

import lombok.Data;

@Data
public class Humidity extends BaseDetector {
    private Float humidity;

    @Override
    public String toString() {
        return "Humidity{" +
                "humidity=" + humidity +
                " " + super.toString() +
                '}';
    }
}
