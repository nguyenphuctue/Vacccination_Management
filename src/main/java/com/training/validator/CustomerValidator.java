package com.training.validator;

import com.training.dto.CustomerDto;
import com.training.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CustomerValidator implements Validator {

    private final CustomerService customerService;
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDto customer = (CustomerDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identityCard", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "captcha", "NotEmpty.customer");
        if (customer.getCustomerId() == 0) {
            if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
                errors.rejectValue("password", "NotEmpty.customer");
            }
        }
        if (!errors.hasFieldErrors("password")) {
            if (customer.getConfirmPassword() == null || customer.getConfirmPassword().isEmpty()) {
                errors.rejectValue("confirmPassword", "NotEmpty.customer");
            }
        }

        if (!errors.hasFieldErrors("email") && !this.emailValidator.isValid(customer.getEmail())) {
            errors.rejectValue("email", "Pattern.customer.email");
        }
        if (!errors.hasFieldErrors("phone") && !Pattern.matches("(0)+([0-9]{9})", customer.getPhone())) {
            errors.rejectValue("phone", "Pattern.customer.phone");
        }

        if (!errors.hasFieldErrors("userName")) {
            Optional<CustomerDto> optionalCustomer = customerService.findByUserName(customer.getUserName());
            if (optionalCustomer.isPresent() && optionalCustomer.get().getCustomerId() != customer.getCustomerId()) {
                errors.rejectValue("userName", "Duplicate.customer.userName");
            }
        }
        if (!errors.hasFieldErrors("email")) {
            Optional<CustomerDto> optionalCustomer = customerService.findByEmail(customer.getEmail());
            if (optionalCustomer.isPresent() && optionalCustomer.get().getCustomerId() != customer.getCustomerId()) {
                errors.rejectValue("email", "Duplicate.customer.email");
            }
        }

        if (!errors.hasFieldErrors("confirmPassword")) {
            if (!customer.getPassword().equals(customer.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Match.customer.confirmPassword");
            }
        }

        if (!errors.hasErrors()) {
            if (!customer.getCaptcha().equals(customer.getCode())) {
                errors.rejectValue("captcha", "Match.customer.captcha");
            }
        }

    }
}
