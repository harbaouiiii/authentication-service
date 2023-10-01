package com.example.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmailExistsException() {
        super("Email already exists");
    }

    public EmailExistsException(String message) {
        super(message);
    }
}
