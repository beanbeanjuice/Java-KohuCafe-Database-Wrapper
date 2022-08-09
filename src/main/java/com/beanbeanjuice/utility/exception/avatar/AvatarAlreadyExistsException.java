package com.beanbeanjuice.utility.exception.avatar;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} used for when an {@link com.beanbeanjuice.tables.avatar.Avatar Avatar} already exists.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class AvatarAlreadyExistsException extends RuntimeException {

    /**
     * Creates a new {@link AvatarAlreadyExistsException} object.
     * @param userID The {@link String user ID}.
     */
    public AvatarAlreadyExistsException(@NotNull String userID) {
        super("The avatar for user (" + userID + ") already exists.");
    }

}
