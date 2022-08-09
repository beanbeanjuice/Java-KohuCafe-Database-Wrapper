package com.beanbeanjuice.utility.exception.user;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link RuntimeException} used for when a {@link com.beanbeanjuice.tables.users.User User} already has a {@link com.beanbeanjuice.tables.ranks.Rank Rank}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserAlreadyHasRankException extends RuntimeException {

    /**
     * Creates a new {@link UserAlreadyHasRankException} object.
     * @param userID The {@link String user ID} specified.
     * @param rankID The {@link Integer rank ID} specified.
     */
    public UserAlreadyHasRankException(@NotNull String userID, @NotNull Integer rankID) {
        super("User (" + userID + ") already has the rank (" + rankID + ".");
    }

}
