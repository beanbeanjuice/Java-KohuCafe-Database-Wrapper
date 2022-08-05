package com.beanbeanjuice;

import com.beanbeanjuice.tables.ranks.RankHandler;
import com.beanbeanjuice.utility.sql.SQLConnection;
import org.jetbrains.annotations.NotNull;

/**
 * A class used for the {@link java.sql.Connection connection} to the MySQL database.
 *
 * This class also contains a sub-enum of {@link TYPE} containing which connection to
 * use for the {@link java.sql.Connection MySQL connection}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class KohuCafeDatabaseConnection {

    /**
     * The {@link TYPE type} of the connection.
     */
    public enum TYPE {
        RELEASE ("beanbeanjuice.com", 4000, "KohuCafe"),
        BETA ("beanbeanjuice.com", 4001, "KohuCafe");

        private final String URL;
        private final Integer PORT;
        private final String SCHEMA;

        /**
         * Creates a new static {@link TYPE} object.
         * @param url The {@link String MySQL url}.
         * @param port The {@link Integer MySQL port}.
         * @param schema The {@link String MySQL schema}.
         */
        TYPE(@NotNull String url, @NotNull Integer port, @NotNull String schema) {
            this.URL = url;
            this.PORT = port;
            this.SCHEMA = schema;
        }

        /**
         * @return The {@link String url} for the MySQL database.
         */
        @NotNull
        public String getURL() { return URL; }

        /**
         * @return The {@link String port} for the MySQL database.
         */
        @NotNull
        public Integer getPort() { return PORT; }

        /**
         * @return The {@link String schema} for the MySQL database.
         */
        @NotNull
        public String getSchema() { return SCHEMA; }
    }

    public RankHandler RANKS;

    /**
     * Creates a new {@link KohuCafeDatabaseConnection}.
     * @param username The {@link String username} to be used for the {@link java.sql.Connection connection} to the MySQL database.
     * @param password The {@link String password} for the MySQL database.
     * @param type The {@link TYPE type} of connection to be used.
     */
    public KohuCafeDatabaseConnection(@NotNull String username, @NotNull String password, @NotNull TYPE type) {
        SQLConnection connection = new SQLConnection(type.getURL(), type.getPort(), type.getSchema(), username, password);

        RANKS = new RankHandler(connection);
    }

}
