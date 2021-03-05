package com.example.sooda.validator;

import com.example.sooda.dto.request.RegistrationRequest;
import com.example.sooda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegistrationRequestValidator implements Validator {

    private final UserService userService;

    @Autowired
    public RegistrationRequestValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RegistrationRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationRequest request = (RegistrationRequest) target;
        validateUsername(errors, request);
        validatePassword(errors, request);
    }

    public void validateUsername(Errors errors, RegistrationRequest request) {
        if (request.getUsername().isEmpty()) {
            errors.reject("username",
                    "Username cannot be empty!");
        } else {
            if (userService.existsByUsername(request.getUsername())) {
                errors.reject("username",
                        "The user with this username already exists!");
            } else {
                final String USERNAME_PATTERN = "^[a-zA-Z0-9._]+$";
                final Pattern pattern = Pattern.compile(USERNAME_PATTERN);
                Matcher matcher = pattern.matcher(request.getUsername());
                if (!matcher.matches()) {
                    errors.reject("username",
                            "Username should include only symbols of lowercase[a-z], uppercase[A-Z], numbers[0-9], dot(.) and underline(_)!");
                }
            }
        }
    }

    public void validatePassword(Errors errors, RegistrationRequest request) {
        if (request.getPassword().isEmpty()) {
            errors.reject("password", "Password cannot be empty!");
        } else {
            if (request.getPasswordRepeat().isEmpty()) {
                errors.reject("passwordRepeat", "Password repeat cannot be empty!");
            } else {
                if (!request.getPassword().equals(request.getPasswordRepeat())) {
                    errors.reject("password", "Passwords do not match!");
                } else {
                    final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}";
                    final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
                    Matcher matcher = pattern.matcher(request.getPassword());
                    if (!matcher.matches()) {
                        errors.reject("password",
                                "Password must be minimum eight characters and has at least one uppercase letter, one lowercase letter and one number!");
                    }
                }
            }
        }
    }
}

