package com.beanbeanjuice;

import com.beanbeanjuice.tables.avatar.AvatarHandler;
import com.beanbeanjuice.tables.avatar.AvatarInventoryHandler;
import com.beanbeanjuice.tables.avatar.AvatarItemHandler;
import com.beanbeanjuice.tables.ranks.RankHandler;
import com.beanbeanjuice.tables.ranks.UserRankHandler;
import com.beanbeanjuice.tables.users.UserHandler;
import com.beanbeanjuice.tables.warns.WarnHandler;
import com.beanbeanjuice.utility.sql.SQLConnection;
import org.jetbrains.annotations.NotNull;

import java.util.TimeZone;

/**
 * A class used for the {@link java.sql.Connection connection} to the MySQL database.
 *
 * This class also contains a sub-enum of {@link TYPE} containing which connection to
 * use for the {@link java.sql.Connection MySQL connection}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class KohuCafeAPI {

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

    public AvatarItemHandler AVATAR_ITEMS;
    public RankHandler RANKS;
    public WarnHandler WARNS;
    public AvatarInventoryHandler AVATAR_INVENTORY;
    public UserRankHandler USER_RANKS;
    public UserHandler USERS;
    public AvatarHandler AVATARS;

    /**
     * Creates a new {@link KohuCafeAPI}.
     * @param username The {@link String username} to be used for the {@link java.sql.Connection connection} to the MySQL database.
     * @param password The {@link String password} for the MySQL database.
     * @param type The {@link TYPE type} of connection to be used.
     */
    public KohuCafeAPI(@NotNull String username, @NotNull String password, @NotNull TYPE type) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SQLConnection connection = new SQLConnection(type.getURL(), type.getPort(), type.getSchema(), username, password);

        AVATAR_ITEMS = new AvatarItemHandler(connection);
        RANKS = new RankHandler(connection);
        WARNS = new WarnHandler(connection);
        AVATAR_INVENTORY = new AvatarInventoryHandler(this, connection);
        USER_RANKS = new UserRankHandler(this, connection);
        USERS = new UserHandler(this, connection);
        AVATARS = new AvatarHandler(this, connection);
    }

}
