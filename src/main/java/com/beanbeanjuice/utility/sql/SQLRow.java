package com.beanbeanjuice.utility.sql;

import org.jetbrains.annotations.NotNull;

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
     * Return a {@link String} from the {@link SQLRow}.
     * @param columnName The {@link String name} of the column to get.
     * @return The {@link String value} in the column.
     * @throws NullPointerException Thrown if the column does not exist.
     */
    @NotNull
    public String getAsString(@NotNull String columnName) throws NullPointerException {
        if (!column.containsKey(columnName))
            throw new NullPointerException("Column Name (" + columnName + ") does not exist.");

        return String.valueOf(column.get(columnName));
    }

    /**
     * Return an {@link Integer} from the {@link SQLRow}.
     * @param columnName The {@link String name} of the column to get.
     * @return The {@link Integer value} in the column.
     * @throws NullPointerException Thrown if the column does not exist.
     * @throws NumberFormatException Thrown if the expected value is not an {@link Integer}.
     */
    @NotNull
    public Integer getAsInteger(@NotNull String columnName) throws NullPointerException, NumberFormatException {
        if (!column.containsKey(columnName))
            throw new NullPointerException("Column Name (" + columnName + ") does not exist.");

        return Integer.parseInt(getAsString(columnName));
    }

    /**
     * Return a {@link Double} from the {@link SQLRow}.
     * @param columnName The {@link String name} of the column to get.
     * @return The {@link Integer value} in the column.
     * @throws NullPointerException Thrown if the column does not exist.
     * @throws NumberFormatException Thrown if the expected value is not a {@link Double}.
     */
    @NotNull
    public Double getAsDouble(@NotNull String columnName) throws NullPointerException, NumberFormatException {
        if (!column.containsKey(columnName))
            throw new NullPointerException("Column Name (" + columnName + ") does not exist.");

        return Double.parseDouble(getAsString(columnName));
    }

    /**
     * Return a {@link Boolean} from the {@link SQLRow}.
     * @param columnName The {@link String name} of the column to get.
     * @return  The {@link Boolean value} in the column.
     * @throws NullPointerException Thrown if the column does not exist.
     */
    @NotNull
    public Boolean getAsBoolean(@NotNull String columnName) throws NullPointerException {
        if (!column.containsKey(columnName))
            throw new NullPointerException("Column Name (" + columnName + ") does not exist.");

        return getAsString(columnName).equalsIgnoreCase("1");
    }

}
