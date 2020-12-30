package com.creatision.weather;

import com.creatision.weather.exceptions.WeatherDataNotFetchedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class WeatherProxy {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    public WeatherProxy(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public HttpResponse<String> getWeatherData(String zipCode) throws WeatherDataNotFetchedException, IOException, InterruptedException {
        String uri = String.format(this.weatherApiUrl, zipCode);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleErrors(response.statusCode(), zipCode);
        return response;
    }

    private void handleErrors(int statusCode, String postalCode) throws WeatherDataNotFetchedException {
        if (HttpStatus.valueOf(statusCode).is5xxServerError()){
            throw new WeatherDataNotFetchedException("Seems like there is some problem with the weather service! Try again later.");
        }
        if (HttpStatus.valueOf(statusCode).is4xxClientError()){
            throw new WeatherDataNotFetchedException(String.format("Sorry, couldn't find anything for %s. Are you sure you entered a correct postal code?", postalCode));
        }
    }

}
