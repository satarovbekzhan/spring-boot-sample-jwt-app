package com.example.sooda.dto.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private final Map<String, String> errors;

    public ErrorResponse() {
        this.errors = new HashMap<>();
    }

    public void addError(String code, String message) {
        this.errors.put(code, message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
