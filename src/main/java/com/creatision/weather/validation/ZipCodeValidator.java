package com.creatision.weather.validation;


import com.creatision.weather.exceptions.ZipCodeValidationException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class ZipCodeValidator{

    public void validate(String zipCode) throws ZipCodeValidationException {
        try{
            validateNotEmpty(zipCode);
            validateHasNoSpaces(zipCode);
        }catch(Exception ex){
            throw new ZipCodeValidationException(ex.getMessage());
        }
    }

    private void validateNotEmpty(String zipCode) throws Exception {
       if (zipCode.length() == 0){
           throw new Exception("Postal Code can't be empty");
       }
    }

    private void validateHasNoSpaces(String zipCode) throws Exception {
        if (zipCode.contains(" ")){
            throw new Exception("Postal Code can't contain spaces");
        }
    }

}
