package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.model.TleData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TleParserService {


    public List<TleData> parse(String content) {


        String[] lines = content.split("\\r?\\n");


        List<TleData> satellites = new ArrayList<>();


        for(int i = 0; i < lines.length - 2; i += 3){


            String name = lines[i].trim();

            String lineOne = lines[i + 1];

            String lineTwo = lines[i + 2];


            satellites.add(
                    new TleData(
                            name,
                            lineOne,
                            lineTwo
                    )
            );

        }


        return satellites;

    }

}