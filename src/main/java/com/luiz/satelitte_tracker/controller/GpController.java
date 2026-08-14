package com.luiz.satelitte_tracker.controller;

import com.luiz.satelitte_tracker.model.GpData;
import com.luiz.satelitte_tracker.service.GpDataParserService;
import com.luiz.satelitte_tracker.service.CelestrakService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tle")
public class GpController {


    private final CelestrakService tleService;
    private final GpDataParserService parser;


    public GpController(CelestrakService tleService, GpDataParserService parser){

        this.tleService = tleService;

        this.parser = parser;
    }

    @GetMapping("/all")
    public List<GpData> all(){

        String content = tleService.getStationData();

        return parser.parse(content);

    }

}
