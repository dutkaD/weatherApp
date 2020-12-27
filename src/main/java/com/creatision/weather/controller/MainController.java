package com.creatision.weather.controller;

import com.creatision.weather.WeatherRequest;
import com.creatision.weather.WeatherService;
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
        return "index";
    }


    @PostMapping("/getWeather")
    public String getWeatherForCity(@ModelAttribute("weatherRequest") WeatherRequest request) {

        String response = weatherService.getPostsPlainJSON(request.getZipCode());
        return "index";
    }

}
