package com.beanbeanjuice.tables.users;

import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.tables.ranks.Rank;
import com.beanbeanjuice.utility.exception.user.UserAlreadyExistsException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotExistException;
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
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM users";

        for (SQLRow column : connection.getResultSet(query)) {
            String userID = column.getAsString("user_id");
            Double balance = column.getAsDouble("balance");
            Double multiplier = column.getAsDouble("multiplier");

            users.put(userID, new User(userID, balance, multiplier, api.USER_RANKS.getUserRanks(userID)));
        }
    }

    @Nullable
    public User getUser(@NotNull String userID) {
        if (!users.containsKey(userID))
            return null;

        return users.get(userID);
    }

    @NotNull
    public Boolean addUser(@NotNull String userID) {
        if (users.containsKey(userID))
            throw new UserAlreadyExistsException(userID);

        String query = "INSERT into USERS (user_id) VALUES (?)";

        String[] values = new String[]{userID};

        if (connection.executeQuery(query, values)) {
            users.put(userID, getUserFromDatabase(userID));
            return true;
        }
        return false;
    }

    private User getUserFromDatabase(@NotNull String userID) {
        String query = "SELECT * FROM users WHERE user_id = (?)";

        String[] values = new String[]{userID};

        SQLRow column = connection.getResultSet(query, values).first();
        Double balance = column.getAsDouble("balance");
        Double multiplier = column.getAsDouble("multiplier");

        return new User(userID, balance, multiplier, api.USER_RANKS.getUserRanks(userID));
    }

    @NotNull
    public Boolean updateBalance(@NotNull String userID, @NotNull Double newBalance) throws UserDoesNotExistException {
        if (!users.containsKey(userID))
            throw new UserDoesNotExistException(userID);

        String query = "UPDATE users SET balance = (?) WHERE user_id = (?)";
        String[] values = new String[]{newBalance.toString(), userID};

        if (connection.executeQuery(query, values)) {
            users.get(userID).updateBalance(newBalance);
            return true;
        }
        return false;
    }

    @NotNull
    public Boolean updateMultiplier(@NotNull String userID, @NotNull Double newMultiplier) throws UserDoesNotExistException {
        if (!users.containsKey(userID))
            throw new UserDoesNotExistException(userID);

        String query = "UPDATE users SET multiplier = (?) WHERE user_id = (?)";
        String[] values = new String[]{newMultiplier.toString(), userID};

        if (connection.executeQuery(query, values)) {
            users.get(userID).updateMultiplier(newMultiplier);
            return true;
        }
        return false;
    }

}
