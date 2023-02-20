package com.somesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(Object object) {
        this(object.toString());
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}