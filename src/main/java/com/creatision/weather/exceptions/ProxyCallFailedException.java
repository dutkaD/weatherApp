package com.creatision.weather.exceptions;

public class ProxyCallFailedException extends Exception{

    public ProxyCallFailedException(String message) {
        super(message);
    }

    public ProxyCallFailedException(String zipCode, String country) {
        super(String.format("No weather data for postal code: %s in country %s", zipCode, country));
    }
}
