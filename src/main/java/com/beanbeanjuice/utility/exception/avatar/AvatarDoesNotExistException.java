package com.beanbeanjuice.utility.exception.avatar;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} used for when an {@link com.beanbeanjuice.tables.avatar.Avatar Avatar} does not exist.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class AvatarDoesNotExistException extends RuntimeException {

    /**
     * Creates a new {@link AvatarDoesNotExistException} object.
     * @param userID The {@link String user ID}.
     */
    public AvatarDoesNotExistException(@NotNull String userID) {
        super("The avatar for user (" + userID + ") does not exist.");
    }

}
