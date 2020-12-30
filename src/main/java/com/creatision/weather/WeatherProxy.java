package com.creatision.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    public HttpResponse<String> getWeatherData(String zipCode) throws IOException, InterruptedException {
        String uri = String.format(this.weatherApiUrl, zipCode);



        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());





//        try {
//            String response = this.restTemplate.getForObject(uri, String.class);
//            return mapper.readValue(response, WeatherResponse.class);
//        }catch (HttpClientErrorException ex){
//            throw new WeatherDataNotFoundException(String.format("Sorry, can't find weather data. Are you sure %s is a correct postal code?", zipCode));
//        }

    }

}
