package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

public class UserAlreadyHasRankException extends RuntimeException {

    public UserAlreadyHasRankException(@NotNull String userID, @NotNull Integer rankID) {
        super("User (" + userID + ") already has the rank (" + rankID + ".");
    }

}
