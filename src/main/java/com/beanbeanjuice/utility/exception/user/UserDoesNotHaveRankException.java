package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

public class UserDoesNotHaveRankException extends RuntimeException {

    public UserDoesNotHaveRankException(@NotNull String userID, @NotNull Integer rankID) {
        super("User (" + userID + ") does not have rank (" + rankID + ".");
    }

}
