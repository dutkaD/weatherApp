package com.creatision.weather;

import com.creatision.weather.exceptions.WeatherDataNotFoundException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.WeatherData;
import com.creatision.weather.validation.ZipCodeValidator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherProxy proxy;
    private final ZipCodeValidator validator;

    public WeatherData getWeatherDataForZip(String zipCode) throws IOException, InterruptedException, WeatherDataNotFoundException, ZipCodeValidationException {

        if (zipCode.length() == 0 || zipCode.contains(" ")){
            throw new ZipCodeValidationException("Postal code is not valid");
        }

        HttpResponse<String> response = proxy.getWeatherData(zipCode);
        if (response.statusCode() == 404){
            throw new WeatherDataNotFoundException(String.format("Sorry, couldn't find weather data for: %s. Please check if the postal code correct" , zipCode));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(response.body(), WeatherData.class);
    }

}
