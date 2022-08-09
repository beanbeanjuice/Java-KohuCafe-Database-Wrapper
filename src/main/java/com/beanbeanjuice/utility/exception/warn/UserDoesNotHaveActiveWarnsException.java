package com.beanbeanjuice.utility.exception.warn;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} for when a {@link com.beanbeanjuice.tables.users.User User} does not have any {@link com.beanbeanjuice.tables.warns.Warn active warns}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserDoesNotHaveActiveWarnsException extends RuntimeException {

    /**
     * Creates a new {@link UserDoesNotHaveActiveWarnsException} object.
     * @param userID The {@link String user ID}.
     */
    public UserDoesNotHaveActiveWarnsException(@NotNull String userID) {
        super("User (" + userID + ") does not have any active warns.");
    }

}
