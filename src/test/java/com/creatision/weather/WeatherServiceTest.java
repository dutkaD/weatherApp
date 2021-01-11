package com.creatision.weather;

import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.WeatherResponse;
import com.creatision.weather.validation.ZipCodeValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
    @Mock
    private WeatherProxy proxy;
    @Mock
    private ZipCodeValidator validator;

    @InjectMocks
    private WeatherService service;


    @Test
    public void zipValidationsFailed() throws ZipCodeValidationException {
        WeatherRequest request = new WeatherRequest();
        request.setCountry("de");
        request.setZipCode("80636");

        doThrow(new ZipCodeValidationException("")).when(validator).validate(request.getZipCode());

        assertThrows(ZipCodeValidationException.class, () -> {
            service.getWeatherData(request);
        });

        verify(validator, times(1)).validate(request.getZipCode());
        verifyNoInteractions(proxy);
    }

    @Test
    public void proxyCallFailed() throws Exception {
        WeatherRequest request = new WeatherRequest();
        request.setCountry("de");
        request.setZipCode("80636");


        doThrow(new ProxyCallFailedException("")).when(proxy).getWeatherData(request);

        assertThrows(Exception.class, () -> {
            service.getWeatherData(request);
        });

        verify(validator, times(1)).validate(request.getZipCode());
        verify(proxy, times(1)).getWeatherData(request);
    }

    @Test
    public void zipValidGoodApiResponse() throws Exception {
        WeatherRequest request = new WeatherRequest();
        request.setCountry("de");
        request.setZipCode("80636");

        WeatherResponse weatherResponse = new WeatherResponse();
        when(proxy.getWeatherData(any())).thenReturn(weatherResponse);

        WeatherResponse weatherDataForZip = service.getWeatherData(request);

        assertEquals(weatherDataForZip, weatherResponse);
        verify(validator, times(1)).validate(request.getZipCode());
        verify(proxy, times(1)).getWeatherData(request);
        verify(proxy, times(1)).getWeatherData(request);
    }

}
