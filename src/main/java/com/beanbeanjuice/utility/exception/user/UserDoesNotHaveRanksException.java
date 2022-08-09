package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

public class UserDoesNotHaveRanksException extends RuntimeException {

    public UserDoesNotHaveRanksException(@NotNull String userID) {
        super("User (" + userID + ") does not have any ranks.");
    }

}
