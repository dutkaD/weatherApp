package com.creatision.weather;

import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.WeatherResponse;
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
    public String getWeatherForCity(@ModelAttribute("weatherRequest") WeatherRequest request, Model model){
        try {
            WeatherResponse response = weatherService.getWeatherData(request);
            model.addAttribute("weatherResponse", response);
        } catch (ZipCodeValidationException | ProxyCallFailedException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "index";
    }
}
