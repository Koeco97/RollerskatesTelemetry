package com.telemetry.rollerskates.repository;


import com.telemetry.rollerskates.entity.BaseDetector;

import java.util.List;

public interface BaseRepository{
    void save(BaseDetector baseDetector);
    List<BaseDetector> getMeasure(String start, String end);
}
