package com.example.sooda.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super(MessageFormat.format(
                "User with id '{0}' not found!",
                id
        ));
    }
}
