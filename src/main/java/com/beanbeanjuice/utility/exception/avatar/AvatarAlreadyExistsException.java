package com.beanbeanjuice.utility.exception.avatar;

import org.jetbrains.annotations.NotNull;

public class AvatarAlreadyExistsException extends RuntimeException {

    public AvatarAlreadyExistsException(@NotNull String userID) {
        super("The avatar for user (" + userID + ") already exists.");
    }

}
