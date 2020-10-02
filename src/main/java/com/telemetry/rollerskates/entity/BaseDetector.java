package com.telemetry.rollerskates.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDetector {
    private String measure;
    private LocalDateTime timestamp;
}
