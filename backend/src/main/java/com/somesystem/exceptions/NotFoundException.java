package com.somesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public NotFoundException(Object object) {
        this(object.toString());
    }

    public NotFoundException(String message) {
        super(message);
    }
}