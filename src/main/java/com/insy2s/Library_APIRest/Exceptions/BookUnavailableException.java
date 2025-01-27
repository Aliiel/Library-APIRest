package com.insy2s.Library_APIRest.Exceptions;

public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException (String message) {
        super(message);
    }
}
