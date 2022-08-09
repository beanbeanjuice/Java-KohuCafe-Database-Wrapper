package com.beanbeanjuice.utility.exception;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} used for when there was an error executing a command in the {@link com.beanbeanjuice.utility.sql.SQLConnection connection}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class DataRetrievalException extends RuntimeException {

    /**
     * Creates a new {@link DataRetrievalException} object.
     * @param message The {@link String reason} for the error.
     * @param cause The {@link Throwable cause} of the {@link RuntimeException}.
     */
    public DataRetrievalException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

}
