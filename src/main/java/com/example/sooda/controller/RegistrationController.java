package com.example.sooda.controller;

import com.example.sooda.dto.request.RegistrationRequest;
import com.example.sooda.dto.response.ErrorResponse;
import com.example.sooda.dto.response.RegistrationResponse;
import com.example.sooda.entity.Role;
import com.example.sooda.entity.User;
import com.example.sooda.service.UserService;
import com.example.sooda.validator.RegistrationRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationRequestValidator registrationRequestValidator;


    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder,
                                  RegistrationRequestValidator registrationRequestValidator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.registrationRequestValidator = registrationRequestValidator;
    }

    @InitBinder(value = "registrationRequest")
    public void initRegistrationRequestBinder(WebDataBinder binder) {
        binder.addValidators(registrationRequestValidator);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(
            @RequestBody @Valid RegistrationRequest registrationRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errors = new ErrorResponse();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.addError(objectError.getCode(), objectError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        User user = new User();
        try {
            user.setUsername(registrationRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setActive(true);
            user.setRoles(Set.of(Role.USER));
            user = userService.addUser(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

        return ResponseEntity.ok(new RegistrationResponse(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        ));
    }
}
