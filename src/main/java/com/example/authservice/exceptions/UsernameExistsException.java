package com.example.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameExistsException() {
        super("Username already exists!");
    }
    public UsernameExistsException(String message) {
        super(message);
    }
}
