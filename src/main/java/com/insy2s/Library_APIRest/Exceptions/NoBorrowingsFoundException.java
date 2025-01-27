package com.insy2s.Library_APIRest.Exceptions;

public class NoBorrowingsFoundException extends RuntimeException {

    public NoBorrowingsFoundException(String message) {
        super(message);
    }
}
