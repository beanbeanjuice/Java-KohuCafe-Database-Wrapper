package com.beanbeanjuice.utility.exception;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} for when a specific {@link com.beanbeanjuice.tables.warns.Warn Warn} does not exist.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class WarnDoesNotExistException extends RuntimeException {

    /**
     * Creates a new {@link WarnDoesNotExistException} object.
     * @param warnID The {@link Integer warn ID}.
     */
    public WarnDoesNotExistException(@NotNull Integer warnID) {
        super("Warn (" + warnID + ") does not exist.");
    }

}
