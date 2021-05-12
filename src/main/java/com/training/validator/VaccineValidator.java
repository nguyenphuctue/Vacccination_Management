package com.training.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.training.dto.VaccineDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VaccineValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return VaccineDto.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        VaccineDto vaccine = (VaccineDto) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vaccineId", "NotEmpty.vaccine");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "origin", "NotEmpty.vaccine");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vaccineName", "NotEmpty.vaccine");
        if (vaccine.getVaccineId() == null) {
        	errors.rejectValue("vaccineId", "NotEmpty.vaccine");
        }
    }
}
