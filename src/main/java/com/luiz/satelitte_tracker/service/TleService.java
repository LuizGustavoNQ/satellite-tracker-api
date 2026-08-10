package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.model.TleData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class TleService {


    private final RestClient celestrakClient;


    public TleService(RestClient celestrakClient){

        this.celestrakClient = celestrakClient;

    }


    public String getStationTle(){

        return celestrakClient
                .get()
                .uri("/NORAD/elements/gp.php?GROUP=active&FORMAT=tle")
                .retrieve()
                .body(String.class);

    }

}