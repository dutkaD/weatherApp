package com.creatision.weather.controller;

import com.creatision.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/")
    public String index() {
        return weatherService.getPostsPlainJSON();
    }

}
