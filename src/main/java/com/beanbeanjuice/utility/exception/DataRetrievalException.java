package com.beanbeanjuice.utility.exception;

import org.jetbrains.annotations.NotNull;

public class DataRetrievalException extends RuntimeException {

    public DataRetrievalException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public DataRetrievalException(@NotNull String message) {
        super(message);
    }

    public DataRetrievalException() {
        super();
    }

}
