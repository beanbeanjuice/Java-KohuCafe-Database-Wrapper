package com.beanbeanjuice.utility.sql;

import com.beanbeanjuice.utility.exception.NotConnectedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;

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
    public Boolean executeQuery(@NotNull String query) {
        return executeQuery(query, null);
    }

    @NotNull
    public SQLResult getResultSet(@NotNull String query) {
        return getResultSet(query, null);
    }

    @NotNull
    public Boolean executeQuery(@NotNull String query, @Nullable String[] args) {

        try {
            connect();
        } catch (SQLException e) {
            throw new NotConnectedException("Failed to connect to the database.");
        }

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            for (int i = 1; args != null && i <= args.length; i++)
                statement.setString(i, args[i-1]);

            statement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {  // Closes all the connections.

            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException ignored) { }
        }
    }

    /**
     * Run a {@link PreparedStatement} query on the {@link Connection MySQL connection}.
     * @param query The {@link String query} to run.
     * @param args The {@link ArrayList<String> arguments} to use.
     * @return The finalised {@link SQLResult result}.
     */
    @NotNull
    public SQLResult getResultSet(@NotNull String query, @Nullable String[] args) {

        try {
            connect();
        } catch (SQLException e) {
            throw new NotConnectedException("Failed to connect to the database.");
        }

        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            statement = connection.prepareStatement(query);

            for (int i = 1; args != null && i <= args.length; i++)
                statement.setString(i, args[i-1]);

            results = statement.executeQuery();
            return new SQLResult(results);

        } catch (SQLException e) {
            return new SQLResult(null, false);
        } finally {  // Closes all the connections.

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
