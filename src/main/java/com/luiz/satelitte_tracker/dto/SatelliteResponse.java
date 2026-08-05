package com.luiz.satelitte_tracker.dto;

public record SatelliteResponse(

        String name,

        double latitude,

        double longitude,

        double altitude

) {}