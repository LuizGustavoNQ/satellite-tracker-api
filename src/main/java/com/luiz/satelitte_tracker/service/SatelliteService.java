package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.dto.SatelliteResponse;
import com.luiz.satelitte_tracker.model.SatellitePosition;
import com.luiz.satelitte_tracker.model.TleData;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SatelliteService {

    private final TleService tleService;
    private final TleParserService parser;
    private final OrbitService orbitService;
    private final SatellitePositionCache cache;

    private volatile List<TleData> currentTles = List.of();

    public SatelliteService(
            TleService tleService,
            TleParserService parser,
            OrbitService orbitService,
            SatellitePositionCache cache
    ) {
        this.tleService = tleService;
        this.parser = parser;
        this.orbitService = orbitService;
        this.cache = cache;
    }

    @PostConstruct
    public void init() {
        updateTles();
    }

    @Scheduled(fixedRate = 21600000)
    public void updateTles() {
        String content = tleService.getStationTle();
        currentTles = parser.parse(content);
    }

    @Scheduled(fixedRate = 1000)
    public void updatePositions() {

        Instant now = Instant.now();

        List<SatelliteResponse> satellites = currentTles.stream()
                .map(tle -> {

                    SatellitePosition position =
                            orbitService.calculate(tle, now);

                    return new SatelliteResponse(
                            tle.satelliteName(),
                            position.latitude(),
                            position.longitude(),
                            position.altitude()
                    );
                })
                .toList();

        cache.update(satellites);
    }
}