package com.beanbeanjuice.utility.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.Function;

public class SQLRow {

    HashMap<String, Object> column;

    public SQLRow(@NotNull HashMap<String, Object> column) {
        this.column = column;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <T> T getObject(@NotNull String columnName) {
        if (!column.containsKey(columnName))
            return null;

        return (T) column.get(columnName);
    }

    @Nullable
    public String getAsString(@NotNull String columnName) {
        if (!column.containsKey(columnName))
            return null;

        return String.valueOf(column.get(columnName));
    }

    @Nullable
    public Integer getAsInt(@NotNull String columnName) {
        return getObject(columnName);
    }

    @Nullable
    public Double getAsDouble(@NotNull String columnName) {
        return getObject(columnName);
    }

}
