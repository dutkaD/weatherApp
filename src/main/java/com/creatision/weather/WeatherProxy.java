package com.creatision.weather;

import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
public class WeatherProxy {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Resource
    private RestTemplate restTemplate;

    public WeatherResponse getWeatherData(WeatherRequest request) throws ProxyCallFailedException {

        String weatherUrl = String.format(this.weatherApiUrl, request.getZipCode(), request.getCountry());
        try {
            ResponseEntity<WeatherResponse> response = restTemplate.postForEntity(
                    weatherUrl,
                    request,
                    WeatherResponse.class);
            return response.getBody();

        } catch (HttpStatusCodeException ex) {
            throw new ProxyCallFailedException(request.getZipCode(), request.country);
        } catch (ResourceAccessException ex){
            throw new ProxyCallFailedException("Couldn't find any data! Make sure you have internet connection.");        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
