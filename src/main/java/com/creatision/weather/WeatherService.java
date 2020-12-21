package com.creatision.weather;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON() {
        String url = "https://api.openweathermap.org/data/2.5/weather?zip=80636,de&appid=46cdf5e3eda364b01ba3f504ebb3473c&units=metric";
        return this.restTemplate.getForObject(url, String.class);
    }

}
