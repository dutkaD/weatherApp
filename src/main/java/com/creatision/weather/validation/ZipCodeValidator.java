package com.creatision.weather.validation;


import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class ZipCodeValidator{

    public void validate(String zipCode){
        validateNotEmpty(zipCode);
        validateHasNoSpaces(zipCode);
    }

    private void validateNotEmpty(String zipCode){
       if (zipCode.length() != 0){
           throw new ValidationException("Postal Code can't be empty");
       }
    }

    private void validateHasNoSpaces(String zipCode) {
        if (zipCode.contains(" ")){
            throw new ValidationException("Postal Code can't contain spaces");
        }
    }

}
