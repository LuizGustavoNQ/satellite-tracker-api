package com.luiz.satelitte_tracker.model;

public record Satellite (
    String name,
    double latitude,
    double longitude,
    double altitude
) {}
