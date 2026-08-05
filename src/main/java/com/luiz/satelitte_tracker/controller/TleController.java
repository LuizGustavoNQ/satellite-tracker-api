package com.luiz.satelitte_tracker.controller;

import com.luiz.satelitte_tracker.model.TleData;
import com.luiz.satelitte_tracker.service.TleParserService;
import com.luiz.satelitte_tracker.service.TleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tle")
public class TleController {


    private final TleService tleService;
    private final TleParserService parser;


    public TleController(TleService tleService, TleParserService parser){

        this.tleService = tleService;

        this.parser = parser;
    }

    @GetMapping("/all")
    public List<TleData> all(){

        String content = tleService.getStationTle();

        return parser.parse(content);

    }

}
