package com.beanbeanjuice.utility.exception.warn;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link RuntimeException} for when a {@link com.beanbeanjuice.tables.users.User User} does not have any {@link com.beanbeanjuice.tables.warns.Warn warns}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserDoesNotHaveWarnsException extends RuntimeException {

    /**
     * Creates a new {@link UserDoesNotHaveWarnsException} object.
     * @param userID The {@link String user ID}.
     */
    public UserDoesNotHaveWarnsException(@NotNull String userID) {
        super("User (" + userID + ") does not have any warns.");
    }

}
