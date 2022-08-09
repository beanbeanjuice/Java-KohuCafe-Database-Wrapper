package com.beanbeanjuice.utility.exception.rank;

import org.jetbrains.annotations.NotNull;

/**
 * A class used for when an {@link com.beanbeanjuice.tables.ranks.Rank Rank} does not exist.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class RankDoesNotExistException extends RuntimeException {

    /**
     * Creates a new {@link RankDoesNotExistException}.
     * @param rankID The {@link Integer ID} of the {@link com.beanbeanjuice.tables.ranks.Rank Rank} that does not exist.
     */
    public RankDoesNotExistException(@NotNull Integer rankID) {
        super("The rank (" + rankID + ") does not exists.");
    }

}
