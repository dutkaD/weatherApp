package com.creatision.weather;

import com.creatision.weather.exceptions.ProxyCallFailedException;
import com.creatision.weather.exceptions.ZipCodeValidationException;
import com.creatision.weather.response.MainWeatherData;
import com.creatision.weather.response.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @MockBean
    private WeatherService service;

    @Test
    public void weatherDataFetchedSuccessfully() throws Exception {
        WeatherRequest request = new WeatherRequest();
        request.setCountry("de");
        request.setZipCode("80636");

        WeatherResponse response = buildResponse();

        when(service.getWeatherData(request)).thenReturn(response);

        mockMvc.perform(post("/getWeather").flashAttr("weatherRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("weatherResponse", equalTo(response)))
                .andExpect(model().attribute("errorMessage", equalTo(null)));
    }

    @Test
    public void errorMessage_proxyCallFailed() throws Exception {
        WeatherRequest request = new WeatherRequest();
        request.setCountry("de");
        request.setZipCode("80636");

        when(service.getWeatherData(request)).thenThrow(new ProxyCallFailedException("Failed"));

        mockMvc.perform(post("/getWeather").flashAttr("weatherRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("weatherResponse", equalTo(null)))
                .andExpect(model().attribute("errorMessage", "Failed"));
    }

    @Test
    public void errorMessage_validationFailed() throws Exception {
        WeatherRequest request = new WeatherRequest();
        request.setCountry("de");
        request.setZipCode("80636");

        when(service.getWeatherData(request)).thenThrow(new ZipCodeValidationException("Invalid"));

        mockMvc.perform(post("/getWeather").flashAttr("weatherRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("weatherResponse", equalTo(null)))
                .andExpect(model().attribute("errorMessage", "Invalid"));
    }

    private WeatherResponse buildResponse() {
        WeatherResponse response = new WeatherResponse();
        response.cityName = "Munich";
        MainWeatherData data = new MainWeatherData();
        data.temperature = "5.5";
        response.mainWeatherData = data;
        return response;
    }

}
