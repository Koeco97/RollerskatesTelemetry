package com.telemetry.rollerskates.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDetector {
    private String measure;
    private LocalDateTime timestamp;
}
