package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.model.GpData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GpDataParserService {


    public List<GpData> parse(String content) {


        String[] lines = content.split("\\r?\\n");


        List<GpData> satellites = new ArrayList<>();


        for(int i = 0; i < lines.length - 2; i += 3){


            String name = lines[i].trim();

            String lineOne = lines[i + 1];

            String lineTwo = lines[i + 2];


            satellites.add(
                    new GpData(
                            name,
                            lineOne,
                            lineTwo
                    )
            );

        }


        return satellites;

    }

}