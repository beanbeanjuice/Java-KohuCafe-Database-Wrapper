package com.beanbeanjuice.tables.users;

import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.utility.exception.user.UserAlreadyExistsException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * A class used for handling {@link User} objects.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserHandler {

    private HashMap<String, User> users = new HashMap<>();
    private KohuCafeAPI api;
    private SQLConnection connection;

    /**
     * Create a new {@link UserHandler} object.
     * @param api The {@link KohuCafeAPI api connection}.
     * @param connection The {@link SQLConnection database connection}.
     */
    public UserHandler(@NotNull KohuCafeAPI api, @NotNull SQLConnection connection) {
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

    /**
     * Get a {@link User} from the database.
     * @param userID The {@link String user ID} for that {@link User}.
     * @return The {@link User} specified.
     * @throws UserDoesNotExistException Thrown if the {@link User} does not exist.
     */
    @NotNull
    public User getUser(@NotNull String userID) throws UserDoesNotExistException {
        if (!users.containsKey(userID))
            throw new UserDoesNotExistException(userID);

        return users.get(userID);
    }

    /**
     * Add a new {@link User} to the database.
     * @param userID The {@link String user ID} for that {@link User}.
     * @return True, if the {@link User} was added successfully.
     * @throws UserAlreadyExistsException Thrown if the {@link User} already exists.
     */
    @NotNull
    public Boolean addUser(@NotNull String userID) throws UserAlreadyExistsException {
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

    /**
     * Update the {@link Double balance} for a specified {@link User}.
     * @param userID The {@link String user ID} for that {@link User}.
     * @param newBalance The {@link Double new balance} for that {@link User}.
     * @return True, if the {@link Double balance} was updated successfully.
     * @throws UserDoesNotExistException Thrown if the {@link User} does not exist.
     */
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

    /**
     * Update the {@link Double multiplier} for a specified {@link User}.
     * @param userID The {@link String user ID} for that {@link User}.
     * @param newMultiplier The {@link Double new multiplier} for that {@link User}.
     * @return True, if the {@link Double multiplier} was updated successfully.
     * @throws UserDoesNotExistException Thrown if the {@link User} does not exist.
     */
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

    /**
     * Check if a {@link User} exists.
     * @param userID The {@link String user ID} of the {@link User}.
     * @return True, if the {@link User} exists.
     */
    @NotNull
    public Boolean userExists(@NotNull String userID) {
        return users.containsKey(userID);
    }

}
