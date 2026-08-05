package com.luiz.satelitte_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SatelliteTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				SatelliteTrackerApplication.class,
				args
		);
	}
}
