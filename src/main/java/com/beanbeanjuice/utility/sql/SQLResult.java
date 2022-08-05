package com.beanbeanjuice.utility.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SQLResult implements Iterable<SQLRow> {

    private ArrayList<SQLRow> table = new ArrayList<>();

    public SQLResult(@Nullable ResultSet results) {

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

    @NotNull
    @Override
    public Iterator<SQLRow> iterator() {
        return table.iterator();
    }

}
