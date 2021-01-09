package com.creatision.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherRequest {
    String zipCode;
    String country;
}
