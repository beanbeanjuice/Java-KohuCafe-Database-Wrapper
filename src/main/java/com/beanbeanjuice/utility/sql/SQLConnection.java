package com.beanbeanjuice.utility.sql;

import com.beanbeanjuice.utility.exception.NotConnectedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    // TODO: May have to make this a runnable.
    /**
     * Starts the {@link Connection MySQL Connection}.
     * @return True, if it was successfully started.
     */
    @NotNull
    public Boolean connect() {
        if (connection != null)  // Already connected.
            return false;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            return false;
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
