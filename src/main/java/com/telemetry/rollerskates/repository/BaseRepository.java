package com.telemetry.rollerskates.repository;


import com.telemetry.rollerskates.entity.BaseDetector;

import java.util.List;

public interface BaseRepository<T extends BaseDetector> {
    void save(T detector);

    List<T> getMeasure(String start, String end, Class type);
}
