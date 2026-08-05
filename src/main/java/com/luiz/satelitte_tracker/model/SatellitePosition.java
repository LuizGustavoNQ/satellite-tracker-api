package com.luiz.satelitte_tracker.model;

public record SatellitePosition(
        double latitude,
        double longitude,
        double altitude
){}