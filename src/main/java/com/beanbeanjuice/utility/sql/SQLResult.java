package com.beanbeanjuice.utility.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A class containing the result from a MySQL query.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class SQLResult implements Iterable<SQLRow> {

    private ArrayList<SQLRow> table = new ArrayList<>();
    private Boolean success = true;

    /**
     * Creates a new {@link SQLResult}.
     * @param results The {@link ResultSet} from a corresponding {@link java.sql.Connection connection} query.
     */
    public SQLResult(@Nullable ResultSet results) {
        mapResults(results);
    }

    /**
     * Creates a new {@link SQLResult}.
     * @param results The {@link ResultSet} from a corresponding {@link java.sql.Connection connection} query.
     * @param success True, if the query was successfully run.
     */
    public SQLResult(@Nullable ResultSet results, @NotNull Boolean success) {
        mapResults(results);
        this.success = success;
    }

    private void mapResults(@Nullable ResultSet results) {
        if (results == null)
            return;

        try {
            while (results.next()) {
                HashMap<String, Object> column = new HashMap<>();
                for (int i = 1; i <= results.getMetaData().getColumnCount(); i++) {
                    String name = results.getMetaData().getColumnName(i);
                    Object value = results.getObject(i);
                    column.put(name, value);
                }
                table.add(new SQLRow(column));
            }
        } catch (SQLException ignored) { }
    }

    /**
     * @return True, if the query was run properly.
     */
    @NotNull
    public Boolean getStatus() {
        return success;
    }

    /**
     * A custom {@link Iterator} used for enhanced for loops.
     * @return The {@link Iterable<SQLRow>}.
     */
    @NotNull
    @Override
    public Iterator<SQLRow> iterator() {
        return table.iterator();
    }

    /**
     * Retrieve the first column.
     * @return The first column of the {@link SQLResult}.
     */
    @NotNull
    public SQLRow first() {
        return table.get(0);
    }

}
