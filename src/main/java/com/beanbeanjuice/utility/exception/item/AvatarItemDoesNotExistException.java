package com.beanbeanjuice.utility.exception.item;

import org.jetbrains.annotations.NotNull;

public class AvatarItemDoesNotExistException extends RuntimeException {

    public AvatarItemDoesNotExistException(@NotNull Integer id) {
        super("The item (" + id + ") does not exist.");
    }

}
