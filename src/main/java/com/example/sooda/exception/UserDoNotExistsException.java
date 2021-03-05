package com.example.sooda.exception;

import java.text.MessageFormat;

public class UserDoNotExistsException extends RuntimeException {
    public UserDoNotExistsException(String id) {
        super(MessageFormat.format(
                "User with username '{0}' not found!",
                id
        ));
    }
}