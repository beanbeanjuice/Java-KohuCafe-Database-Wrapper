package com.beanbeanjuice.utility.sql;

import com.beanbeanjuice.utility.exception.DataRetrievalException;
import com.beanbeanjuice.utility.exception.NotConnectedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

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

    /**
     * Executes a {@link String query} on the {@link SQLConnection}.
     * @param query The {@link String query} to execute.
     * @return True, if the {@link String query} was executed successfully.
     * @throws NotConnectedException Thrown if the database is not connected.
     * @throws DataRetrievalException Thrown if there was an error executing the query on the connection.
     */
    @NotNull
    public Boolean executeQuery(@NotNull String query) throws NotConnectedException, DataRetrievalException {
        return executeQuery(query, null);
    }

    /**
     * Executes a {@link String query} on the {@link SQLConnection}.
     * @param query The {@link String query} to execute.
     * @param args A {@link String[]} containing {@link String arguments} to add to a {@link ResultSet}.
     * @return True, if the {@link String query} was executed successfully.
     * @throws NotConnectedException Thrown if the database is not connected.
     * @throws DataRetrievalException Thrown if there was an error executing the query on the connection.
     */
    @NotNull
    public Boolean executeQuery(@NotNull String query, @Nullable String[] args) throws NotConnectedException, DataRetrievalException {

        try {
            connect();
        } catch (SQLException e) {
            throw new NotConnectedException("Failed to connect to the database.", e);
        }

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            for (int i = 1; args != null && i <= args.length; i++)
                statement.setString(i, args[i-1]);

            statement.execute();
            return true;

        } catch (SQLException e) {
            throw new DataRetrievalException("Error retrieving data from the database.", e);
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
     * Executes a {@link String query} on the {@link SQLConnection}.
     * @param query The {@link String query} to execute.
     * @return An {@link SQLResult} that was returned from the {@link String query}.
     * @throws NotConnectedException Thrown if the database is not connected.
     * @throws DataRetrievalException Thrown if there was an error executing the query on the connection.
     */
    @NotNull
    public SQLResult getResultSet(@NotNull String query) throws NotConnectedException, DataRetrievalException {
        return getResultSet(query, null);
    }

    /**
     * Executes a {@link String query} on the {@link SQLConnection}.
     * @param query The {@link String query} to execute.
     * @param args A {@link String[]} containing {@link String arguments} to add to a {@link ResultSet}.
     * @return An {@link SQLResult} that was returned from the {@link String query}.
     * @throws NotConnectedException Thrown if the database is not connected.
     * @throws DataRetrievalException Thrown if there was an error executing the query on the connection.
     */
    @NotNull
    public SQLResult getResultSet(@NotNull String query, @Nullable String[] args) throws NotConnectedException, DataRetrievalException {

        try {
            connect();
        } catch (SQLException e) {
            throw new NotConnectedException("Failed to connect to the database.", e);
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
            throw new DataRetrievalException("Error retrieving a result from the database.", e);
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
    @NotNull
    public Connection getConnection() throws NotConnectedException {
        if (connection == null)
            throw new NotConnectedException("The database has not been connected.");

        return connection;
    }

}
