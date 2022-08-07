package com.beanbeanjuice.utility.exception.rank;

import org.jetbrains.annotations.NotNull;

public class RankDoesNotExistException extends RuntimeException {

    public RankDoesNotExistException(@NotNull Integer rankID) {
        super("The rank (" + rankID + ") does not exists.");
    }

}
