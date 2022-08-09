package com.beanbeanjuice.utility.exception;

import org.jetbrains.annotations.NotNull;

public class WarnDoesNotExistException extends RuntimeException {

    public WarnDoesNotExistException(@NotNull Integer warnID) {
        super("Warn (" + warnID + ") does not exist.");
    }

}
