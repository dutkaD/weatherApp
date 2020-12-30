package com.creatision.weather.controller;

import com.creatision.weather.WeatherRequest;
import com.creatision.weather.WeatherService;
import com.creatision.weather.exceptions.WeatherDataNotFoundException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String getSearchPage(Model model) {
        model.addAttribute("weatherRequest", new WeatherRequest());
        model.addAttribute("weatherResponse");

        return "index";
    }


    @PostMapping("/getWeather")
    public String getWeatherForCity(@ModelAttribute("weatherRequest") @Valid WeatherRequest request, Model model) throws IOException, InterruptedException {

        try {
            WeatherData response = weatherService.getWeatherDataForZip(request.getZipCode());
            model.addAttribute("weatherRequest", new WeatherRequest());
            model.addAttribute("weatherResponse", response);
        }catch (WeatherDataNotFoundException | ZipCodeValidationException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "index";
    }

}
