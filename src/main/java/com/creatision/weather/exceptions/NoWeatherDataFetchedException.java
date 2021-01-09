package com.creatision.weather.exceptions;

public class NoWeatherDataFetchedException extends Exception{
    public NoWeatherDataFetchedException(String message) {
        super(message);
    }
}
