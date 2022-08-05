package com.beanbeanjuice.utility.sql;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private Connection connection;
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public SQLConnection(@NotNull String url, @NotNull Integer port, @NotNull String schema,
                         @NotNull String username, @NotNull String password) {
        this.URL = "jdbc:mysql://" + url + ":" + port + "/" + schema;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    // TODO: May have to make this a runnable.
    @NotNull
    public Boolean start() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @NotNull
    public Connection getConnection() {
        return connection;
    }

}
