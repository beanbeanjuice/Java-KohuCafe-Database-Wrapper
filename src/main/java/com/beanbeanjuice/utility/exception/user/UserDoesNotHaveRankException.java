package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} used for when a {@link com.beanbeanjuice.tables.users.User User} does not have a {@link com.beanbeanjuice.tables.ranks.Rank Rank}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserDoesNotHaveRankException extends RuntimeException {

    /**
     * Creates a new {@link UserDoesNotHaveRankException} object.
     * @param userID The {@link String user ID}.
     * @param rankID The {@link Integer rank ID}.
     */
    public UserDoesNotHaveRankException(@NotNull String userID, @NotNull Integer rankID) {
        super("User (" + userID + ") does not have rank (" + rankID + ".");
    }

}
