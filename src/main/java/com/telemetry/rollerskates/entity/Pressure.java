package com.telemetry.rollerskates.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Pressure {
    private Float pressure;
    private String measure;
    @JsonIgnore
    private LocalDateTime dateTime;
}
