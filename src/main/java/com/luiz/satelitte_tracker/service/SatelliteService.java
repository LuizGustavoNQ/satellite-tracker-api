package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.websocket.SatelliteWebSocketHandler;
import com.luiz.satelitte_tracker.dto.SatelliteResponse;
import com.luiz.satelitte_tracker.model.SatellitePosition;
import com.luiz.satelitte_tracker.model.TleData;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;

@Service
public class SatelliteService {

    private final TleService tleService;
    private final TleParserService parser;
    private final OrbitService orbitService;
    private final SatellitePositionCache cache;
    private final SatelliteWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    private volatile List<TleData> currentTles = List.of();

    public SatelliteService(
            TleService tleService,
            TleParserService parser,
            OrbitService orbitService,
            SatellitePositionCache cache,
            SatelliteWebSocketHandler webSocketHandler,
            ObjectMapper objectMapper
    ) {
        this.tleService = tleService;
        this.parser = parser;
        this.orbitService = orbitService;
        this.cache = cache;
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {

        try {
            updateTles();
        } catch (Exception e) {
            System.out.println(
                    "Não foi possível carregar os TLEs na inicialização: "
                            + e.getMessage()
            );
        }
    }

    @Scheduled(fixedRate = 21600000)
    public void updateTles() {

        try {

            String content = tleService.getStationTle();

            List<TleData> newTles = parser.parse(content);

            if (!newTles.isEmpty()) {
                currentTles = newTles;

                System.out.println(
                        "TLE atualizado: " + newTles.size() + " satélites"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Falha ao atualizar TLE. Mantendo dados anteriores: "
                            + e.getMessage()
            );
        }
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

        try {
            String json = objectMapper.writeValueAsString(satellites);
            webSocketHandler.broadcast(json);
        } catch (Exception e) {
            System.out.println(
                    "Erro ao enviar posições pelo WebSocket: "
                            + e.getMessage()
            );
        }
    }
}