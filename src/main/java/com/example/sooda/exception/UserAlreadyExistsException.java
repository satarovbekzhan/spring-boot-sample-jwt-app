package com.example.sooda.exception;

import java.text.MessageFormat;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super(MessageFormat.format(
                "User with username '{0}' already exists!",
                username
        ));
    }
}