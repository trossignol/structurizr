package com.example.demo;

import com.example.demo.dto.Weather;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherClient {
    private final WebClient client = WebClient.create("https://api.open-meteo.com/v1");

    @RegisterReflectionForBinding(Weather.class)
    public Mono<Weather> get() {
        return client.get()
                .uri("/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m")
                .retrieve()
                .bodyToMono(Weather.class);
    }


}