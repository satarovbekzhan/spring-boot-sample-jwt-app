package com.example.sooda.validator;

import org.springframework.validation.Errors;

public interface Validator extends org.springframework.validation.Validator {
    @Override
    boolean supports(Class<?> clazz);
    @Override
    void validate(Object target, Errors errors);
}
