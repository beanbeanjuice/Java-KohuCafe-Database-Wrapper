package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} used for when a {@link com.beanbeanjuice.tables.users.User User} does not exist.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserDoesNotExistException extends RuntimeException {

    /**
     * Creates a new {@link UserDoesNotExistException} object.
     * @param userID The {@link String user ID} specified.
     */
    public UserDoesNotExistException(@NotNull String userID) {
        super("The user (" + userID + ") does not exists.");
    }

}
