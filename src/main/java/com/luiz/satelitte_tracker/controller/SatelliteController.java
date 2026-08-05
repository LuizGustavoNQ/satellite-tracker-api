package com.luiz.satelitte_tracker.controller;

import com.luiz.satelitte_tracker.dto.SatelliteResponse;
import com.luiz.satelitte_tracker.service.SatellitePositionCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SatelliteController {

    private final SatellitePositionCache cache;

    public SatelliteController(SatellitePositionCache cache) {
        this.cache = cache;
    }


    @GetMapping("/api/satellites")
    public List<SatelliteResponse> getSatellites() {

        return cache.get();

    }
}