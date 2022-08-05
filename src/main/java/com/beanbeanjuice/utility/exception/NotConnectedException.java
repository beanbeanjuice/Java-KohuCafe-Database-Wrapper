package com.beanbeanjuice.utility.exception;

import org.jetbrains.annotations.NotNull;

/**
 * A class used for {@link RuntimeException RuntimeExceptions} caused when attempting
 * to connect to the MySQL database.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class NotConnectedException extends RuntimeException {

    /**
     * Creates a new {@link NotConnectedException}.
     * @param message The {@link String message} to send when throwing the {@link RuntimeException}.
     * @param cause The {@link Throwable cause} of the {@link RuntimeException}.
     */
    public NotConnectedException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new {@link NotConnectedException}.
     * @param message The {@link String message} to send when throwing the {@link RuntimeException}.
     */
    public NotConnectedException(@NotNull String message) {
        super(message);
    }

    /**
     * Creates a new {@link NotConnectedException}.
     */
    public NotConnectedException() {
        super();
    }

}
