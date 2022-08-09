package com.beanbeanjuice.utility.exception.item;

import org.jetbrains.annotations.NotNull;

public class AvatarInventoryDoesNotExistException extends RuntimeException {

    public AvatarInventoryDoesNotExistException(@NotNull String userID) {
        super("The avatar inventory for user (" + userID + ") does not exist.");
    }

}
