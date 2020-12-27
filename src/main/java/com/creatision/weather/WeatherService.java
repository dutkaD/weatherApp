package com.creatision.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String articleShopUrl;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON(String zipCode) {
        String weatherApiUrl = String.format(articleShopUrl, zipCode);

        return this.restTemplate.getForObject(weatherApiUrl, String.class);
    }

}
