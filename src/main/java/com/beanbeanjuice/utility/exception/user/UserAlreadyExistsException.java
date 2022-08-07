package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(@NotNull String userID) {
        super("The user (" + userID + ") already exists.");
    }

}
