package com.creatision.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class WeatherResponse {

    @JsonProperty("main")
    public MainWeatherData mainWeatherData;

    @JsonProperty("name")
    public String cityName;
}
