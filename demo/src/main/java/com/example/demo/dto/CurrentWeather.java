package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentWeather(
        @JsonProperty("temperature_2m") double temperature,
        @JsonProperty("wind_speed_10m") double windSpeed) {
}
