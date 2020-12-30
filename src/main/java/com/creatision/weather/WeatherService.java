package com.creatision.weather;

import com.creatision.weather.exceptions.WeatherDataNotFetchedException;
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

    public WeatherData getWeatherDataForZip(String zipCode) throws IOException, InterruptedException, WeatherDataNotFetchedException, ZipCodeValidationException {
        validator.validate(zipCode);

        HttpResponse<String> response = proxy.getWeatherData(zipCode);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(response.body(), WeatherData.class);
    }

}
