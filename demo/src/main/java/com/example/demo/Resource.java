package com.example.demo;

import com.example.demo.dto.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Resource {

    @Autowired
    WeatherClient client;

    @GetMapping("/api/weather")
    public Mono<Weather> get() {
        return this.client.get();
    }
}