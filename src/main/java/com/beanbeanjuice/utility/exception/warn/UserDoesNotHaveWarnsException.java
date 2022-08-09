package com.beanbeanjuice.utility.exception.warn;

import org.jetbrains.annotations.NotNull;

public class UserDoesNotHaveWarnsException extends RuntimeException {

    public UserDoesNotHaveWarnsException(@NotNull String userID) {
        super("User (" + userID + ") does not have any warns.");
    }

}
