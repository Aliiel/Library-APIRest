package com.insy2s.Library_APIRest.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NullPointerException extends RuntimeException {

    public NullPointerException (String message) {
        super(message);
    }
}
