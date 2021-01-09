package com.creatision.weather.controller;

import com.creatision.weather.WeatherRequest;
import com.creatision.weather.WeatherService;
import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String getWeatherForCity(@ModelAttribute("weatherRequest") @Valid WeatherRequest request, Model model){
        try {
            WeatherResponse response = weatherService.getWeatherData(request);
            model.addAttribute("weatherRequest", new WeatherRequest());
            model.addAttribute("weatherResponse", response);
        } catch (ZipCodeValidationException | ProxyCallFailedException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "index";
    }

    // todo: check RestControllerExceptionHandler

}
