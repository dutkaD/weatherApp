package com.creatision.weather;

import com.creatision.weather.exceptions.NoWeatherDataFetchedException;
import com.creatision.weather.response.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;


@Component
@Slf4j
public class ResponseMapper {
    public WeatherResponse mapToWeatherData(HttpResponse<String> response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            return mapper.readValue(response.body(), WeatherResponse.class);
        }catch (JsonProcessingException ex){
            throw new NoWeatherDataFetchedException("Can't find weather data for the given postal code and country.");
        }
    }
}
