package com.beanbeanjuice.utility.exception.warn;

import org.jetbrains.annotations.NotNull;

public class UserDoesNotHaveActiveWarnsException extends RuntimeException {

    public UserDoesNotHaveActiveWarnsException(@NotNull String userID) {
        super("User (" + userID + ") does not have any active warns.");
    }

}
