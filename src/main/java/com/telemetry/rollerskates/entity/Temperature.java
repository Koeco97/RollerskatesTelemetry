package com.telemetry.rollerskates.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Temperature {
    private Float temperature;
    private String measure;
    @JsonIgnore
    private LocalDateTime dateTime;
}
