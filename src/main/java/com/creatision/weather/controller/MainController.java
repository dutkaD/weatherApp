package com.creatision.weather.controller;

import com.creatision.weather.WeatherRequest;
import com.creatision.weather.WeatherService;
import com.creatision.weather.exceptions.WeatherDataNotFoundException;
import com.creatision.weather.response.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


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
    public String getWeatherForCity(@ModelAttribute("weatherRequest") WeatherRequest request, Model model) throws JsonProcessingException{
        try {
            WeatherResponse response = weatherService.getWeatherData(request.getZipCode());
            model.addAttribute("weatherRequest", new WeatherRequest());
            model.addAttribute("weatherResponse", response);

        }catch (WeatherDataNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "index";
    }

}
