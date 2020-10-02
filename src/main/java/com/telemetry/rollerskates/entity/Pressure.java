package com.telemetry.rollerskates.entity;

import lombok.Data;

@Data
public class Pressure extends BaseDetector {
    private Float pressure;

    @Override
    public String toString() {
        return "Pressure{" +
                "Pressure=" + pressure +
                " " + super.toString() +
                '}';
    }
}
