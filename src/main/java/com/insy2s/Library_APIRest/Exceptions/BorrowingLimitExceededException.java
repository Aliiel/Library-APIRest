package com.insy2s.Library_APIRest.Exceptions;

public class BorrowingLimitExceededException extends RuntimeException {

    public BorrowingLimitExceededException (String message) {
        super(message);
    }
}
