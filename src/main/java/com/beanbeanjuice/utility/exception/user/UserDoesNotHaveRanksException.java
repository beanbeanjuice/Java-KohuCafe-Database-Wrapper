package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} for when a {@link com.beanbeanjuice.tables.users.User User} does not have any {@link com.beanbeanjuice.tables.ranks.Rank ranks}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserDoesNotHaveRanksException extends RuntimeException {

    /**
     * Creates a new {@link UserDoesNotHaveRanksException}.
     * @param userID The {@link String user ID}.
     */
    public UserDoesNotHaveRanksException(@NotNull String userID) {
        super("User (" + userID + ") does not have any ranks.");
    }

}
