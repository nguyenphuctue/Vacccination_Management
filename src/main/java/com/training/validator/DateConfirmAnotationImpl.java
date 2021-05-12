package com.training.validator;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;


public class DateConfirmAnotationImpl implements ConstraintValidator<DateConfirmAnotation, Object> {
	private String first;
	private String second;
	
	@Override
	public void initialize(DateConfirmAnotation constraintAnnotation) {
		first = constraintAnnotation.From();
		second = constraintAnnotation.To();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		Object from = new BeanWrapperImpl(value).getPropertyValue(first);
		Object to = new BeanWrapperImpl(value).getPropertyValue(second);
		if (from != null && to != null && ((Date) from).before((Date) to)) {
			return true;
		}
		return false;
	}
}
