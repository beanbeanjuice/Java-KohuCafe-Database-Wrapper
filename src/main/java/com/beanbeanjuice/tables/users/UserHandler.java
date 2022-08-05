package com.beanbeanjuice.tables.users;

import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.tables.ranks.Rank;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHandler {

    private HashMap<String, User> users = new HashMap<>();
    private KohuCafeDatabaseConnection api;
    private SQLConnection connection;

    public UserHandler(@NotNull KohuCafeDatabaseConnection api, @NotNull SQLConnection connection) {
        this.api = api;
        this.connection = connection;
        startCache();
    }

    private void startCache() {
        String query = "SELECT * FROM users";

        for (SQLRow column : connection.runStatement(query)) {
            String userID = column.getAsString("user_id");
            Double balance = column.getAsDouble("balance");
            Double multiplier = column.getAsDouble("multiplier");
            ArrayList<Rank> ranks = api.RANKS.getUserRanks(userID);

            users.put(userID, new User(userID, balance, multiplier, ranks));
        }
    }

    @Nullable
    public User getUser(@NotNull String userID) {
        if (!users.containsKey(userID))
            return null;

        return users.get(userID);
    }

}
