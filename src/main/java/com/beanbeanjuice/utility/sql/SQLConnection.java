package com.beanbeanjuice.utility.sql;

import com.beanbeanjuice.utility.exception.NotConnectedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class used for connecting to a MySQL database.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class SQLConnection {

    private Connection connection;
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    /**
     * Creates a new {@link SQLConnection} object.
     *
     * This contains a {@link Connection} to the MySQL database.
     *
     * @param url The {@link String MySQL URL} of the database.
     * @param port The {@link Integer port} of the database.
     * @param schema The {@link String schema} inside the database.
     * @param username The {@link String login name} of the database.
     * @param password The {@link String password} needed to connect to the database.
     */
    public SQLConnection(@NotNull String url, @NotNull Integer port, @NotNull String schema,
                         @NotNull String username, @NotNull String password) {
        this.URL = "jdbc:mysql://" + url + ":" + port + "/" + schema;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    /**
     * Starts the {@link Connection MySQL Connection}.
     */
    private void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @NotNull
    public SQLResult runStatement(@NotNull String query) {
        return runPreparedStatement(query, null);
    }

    @NotNull
    public SQLResult runPreparedStatement(@NotNull String query, @Nullable ArrayList<String> args) {

        try {
            connect();
        } catch (SQLException e) {
            throw new NotConnectedException("Failed to connect to the database.");
        }

        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            statement = connection.prepareStatement(query);

            for (int i = 1; args != null && i < args.size(); i++)
                statement.setString(i, args.get(i));

            results = statement.executeQuery();

            return new SQLResult(results);

        } catch (SQLException e) {
            return new SQLResult(null);
        } finally {

            try {
                if (results != null)
                    results.close();
            } catch (SQLException ignored) { }

            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ignored) { }

            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException ignored) { }
        }
    }

    /**
     * @return The current {@link Connection MySQL connection}.
     * @throws NotConnectedException Thrown if the database has not been connected.
     */
    @Nullable
    public Connection getConnection() throws NotConnectedException {
        if (connection == null)
            throw new NotConnectedException("The database has not been connected.");

        return connection;
    }

}
