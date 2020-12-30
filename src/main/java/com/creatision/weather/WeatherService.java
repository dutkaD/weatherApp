package com.creatision.weather;

import com.creatision.weather.exceptions.WeatherDataNotFoundException;
import com.creatision.weather.response.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public WeatherResponse getWeatherData(String zipCode) throws WeatherDataNotFoundException, JsonProcessingException {
        String uri = String.format(this.weatherApiUrl, zipCode);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String response = this.restTemplate.getForObject(uri, String.class);
            return mapper.readValue(response, WeatherResponse.class);
        }catch (HttpClientErrorException ex){
            throw new WeatherDataNotFoundException(String.format("Sorry, can't find weather data. Are you sure %s is a correct postal code?", zipCode));
        }

    }

}
