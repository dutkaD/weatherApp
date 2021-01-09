package com.creatision.weather;

import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.WeatherResponse;
import com.creatision.weather.validation.ZipCodeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherProxy proxy;
    private final ZipCodeValidator validator;

    public WeatherResponse getWeatherData(WeatherRequest request) throws ZipCodeValidationException, ProxyCallFailedException {
        validator.validate(request.getZipCode());
        return proxy.getWeatherData(request);
    }

}
