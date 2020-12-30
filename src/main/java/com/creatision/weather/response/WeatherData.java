package com.creatision.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class WeatherData {

    @JsonProperty("main")
    public MainWeatherData mainWeatherData;
}
