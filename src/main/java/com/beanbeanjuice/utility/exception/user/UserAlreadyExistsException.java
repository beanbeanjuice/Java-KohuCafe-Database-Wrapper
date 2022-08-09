package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link RuntimeException} used for when a {@link com.beanbeanjuice.tables.users.User User} already exists.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Creates a new {@link UserAlreadyExistsException} object.
     * @param userID The {@link String user ID} specified.
     */
    public UserAlreadyExistsException(@NotNull String userID) {
        super("The user (" + userID + ") already exists.");
    }

}
