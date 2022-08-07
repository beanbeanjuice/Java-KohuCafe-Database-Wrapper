package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(@NotNull String userID) {
        super("The user (" + userID + ") does not exists.");
    }

}
