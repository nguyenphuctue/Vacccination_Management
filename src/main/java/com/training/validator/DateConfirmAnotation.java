package com.training.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateConfirmAnotationImpl.class)
public @interface DateConfirmAnotation {
	String From();
	String To();
	String message() default "{MSG14}";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
	
	@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		DateConfirmAnotation[] value();
	}
}
