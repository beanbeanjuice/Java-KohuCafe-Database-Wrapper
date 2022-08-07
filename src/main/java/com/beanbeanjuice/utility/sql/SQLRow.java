package com.beanbeanjuice.utility.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * A row containing SQL database data.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class SQLRow {

    HashMap<String, Object> column;

    /**
     * Create a new {@link SQLRow}.
     * @param column A {@link HashMap} containing a {@link String} key and a {@link Object} value.
     */
    public SQLRow(@NotNull HashMap<String, Object> column) {
        this.column = column;
    }

    /**
     * Get the value as a {@link String}.
     * @param columnName The {@link String name} of the column.
     * @return The {@link String value} of the map.
     */
    @Nullable
    public String getAsString(@NotNull String columnName) {
        if (!column.containsKey(columnName))
            return null;

        return String.valueOf(column.get(columnName));
    }

    /**
     * Get the value as a {@link Integer}.
     * @param columnName The {@link String name} of the column.
     * @return The {@link Integer value} of the map.
     */
    @Nullable
    public Integer getAsInteger(@NotNull String columnName) {
        if (!column.containsKey(columnName))
            return null;

        try {
            return Integer.parseInt(getAsString(columnName));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Get the value as a {@link Double}.
     * @param columnName The {@link String name} of the column.
     * @return The {@link Double value} of the map.
     */
    @Nullable
    public Double getAsDouble(@NotNull String columnName) {
        if (!column.containsKey(columnName))
            return null;

        try {
            return Double.parseDouble(getAsString(columnName));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Get the value as a {@link Boolean}.
     * @param columnName The {@link String name} of the column.
     * @return The {@link Double value} of the map.
     */
    @Nullable
    public Boolean getAsBoolean(@NotNull String columnName) {
        if (!column.containsKey(columnName))
            return null;

        try {
            return getAsString(columnName).equalsIgnoreCase("1");
        } catch (NullPointerException e) {
            return null;
        }
    }

}
