package com.creatision.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class MainWeatherData {

    @JsonProperty("temp")
    public String temperature;

    @JsonProperty("feels_like")
    public String temperatureFeels;

    @JsonProperty("temp_min")
    public String temperatureMin;

    @JsonProperty("temp_max")
    public String temperatureMax;
}
