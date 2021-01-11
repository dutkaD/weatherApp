package com.creatision.weather;

import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.response.MainWeatherData;
import com.creatision.weather.response.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherProxyTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherProxy proxy;

    @Before
    public void whenSigningCompleteSetsToken(){
        ReflectionTestUtils.setField(proxy, "weatherApiUrl", "http://zipcode_%s_country_%s");
    }

    @Test
    public void apiCall_throwsResourceAccessException(){
        WeatherRequest request = new WeatherRequest();
        request.setZipCode("80636");
        request.setCountry("de");
        when(restTemplate.postForEntity("http://zipcode_80636_country_de", request, WeatherResponse.class))
                .thenThrow(new ResourceAccessException(""));

        assertThrows(ProxyCallFailedException.class, () -> {
            proxy.getWeatherData(request);
        });
    }


    @Test
    public void apiCall_throwsHttpStatusCodeException(){
        WeatherRequest request = new WeatherRequest();
        request.setZipCode("80636");
        request.setCountry("de");
        when(restTemplate.postForEntity("http://zipcode_80636_country_de", request, WeatherResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(ProxyCallFailedException.class, () -> {
            proxy.getWeatherData(request);
        });
    }

    @Test
    public void apiCall_successful() throws ProxyCallFailedException {
        WeatherRequest request = new WeatherRequest();
        request.setZipCode("80636");
        request.setCountry("de");

        WeatherResponse expectedResponse = buildResponse();

        when(restTemplate.postForEntity("http://zipcode_80636_country_de", request, WeatherResponse.class))
                .thenReturn(new ResponseEntity(expectedResponse, HttpStatus.OK));

        WeatherResponse response = proxy.getWeatherData(request);

        assertEquals(response.cityName, expectedResponse.cityName);
        assertEquals(response.mainWeatherData.temperature, expectedResponse.mainWeatherData.temperature);
        assertEquals(response.mainWeatherData.temperatureFeels, expectedResponse.mainWeatherData.temperatureFeels);
        assertEquals(response.mainWeatherData.humidity, expectedResponse.mainWeatherData.humidity);
        assertEquals(response.mainWeatherData.pressure, expectedResponse.mainWeatherData.pressure);
    }

    private WeatherResponse buildResponse() {
        WeatherResponse expectedResponse = new WeatherResponse();
        expectedResponse.cityName = "Munich";
        expectedResponse.mainWeatherData = new MainWeatherData();
        expectedResponse.mainWeatherData.temperature = "5.5";
        expectedResponse.mainWeatherData.temperatureFeels = "5.6";
        expectedResponse.mainWeatherData.humidity = "45";
        expectedResponse.mainWeatherData.pressure = "5";
        return expectedResponse;
    }
}
