package com.beanbeanjuice.utility.exception;

public class WarnDoesNotExistException extends RuntimeException {

    public WarnDoesNotExistException() {
        super("The specified warn does not exist.");
    }

}
