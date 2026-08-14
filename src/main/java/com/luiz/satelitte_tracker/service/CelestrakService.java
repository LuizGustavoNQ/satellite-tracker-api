package com.luiz.satelitte_tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CelestrakService {

    private final RestClient celestrakClient;

    public CelestrakService(RestClient celestrakClient) {
        this.celestrakClient = celestrakClient;
    }

    public String getStationData() {
        return celestrakClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/NORAD/elements/gp.php")
                        .queryParam("CATNR", 25544)
                        .queryParam("FORMAT", "CSV")
                        .build())
                .retrieve()
                .body(String.class);
    }
}