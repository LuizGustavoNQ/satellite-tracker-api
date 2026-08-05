package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.dto.SatelliteResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SatellitePositionCache {

    private List<SatelliteResponse> satellites = List.of();

    public void update(List<SatelliteResponse> satellites) {
        this.satellites = satellites;
    }

    public List<SatelliteResponse> get() {
        return satellites;
    }
}