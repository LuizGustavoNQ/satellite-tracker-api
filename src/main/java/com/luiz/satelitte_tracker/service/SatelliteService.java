package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.dto.SatelliteResponse;
import com.luiz.satelitte_tracker.model.SatellitePosition;
import com.luiz.satelitte_tracker.model.TleData;
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

    public List<SatelliteResponse> calculateSatellites() {

        String content = tleService.getStationTle();

        List<TleData> tles = parser.parse(content);

        return tles.stream()
                .map(tle -> {

                    SatellitePosition position =
                            orbitService.calculate(
                                    tle,
                                    Instant.now()
                            );

                    return new SatelliteResponse(
                            tle.satelliteName(),
                            position.latitude(),
                            position.longitude(),
                            position.altitude()
                    );

                })
                .toList();
    }


    @Scheduled(fixedRate = 100)
    public void updatePositions() {

        List<SatelliteResponse> satellites =
                calculateSatellites();

        cache.update(satellites);
    }
}