package com.telemetry.rollerskates.entity;

import lombok.Data;

@Data
public class Speed extends BaseDetector {
    private Float speed;

    @Override
    public String toString() {
        return "Speed{" +
                "Speed=" + speed +
                " " + super.toString() +
                '}';
    }
}
