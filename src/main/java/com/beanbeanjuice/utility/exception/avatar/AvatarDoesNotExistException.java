package com.beanbeanjuice.utility.exception.avatar;

import org.jetbrains.annotations.NotNull;

public class AvatarDoesNotExistException extends RuntimeException {

    public AvatarDoesNotExistException(@NotNull String userID) {
        super("The avatar for user (" + userID + ") does not exist.");
    }

}
