package com.beanbeanjuice.tables.ranks;

import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.utility.exception.rank.RankDoesNotExistException;
import com.beanbeanjuice.utility.exception.user.UserAlreadyHasRankException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotExistException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotHaveRankException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotHaveRanksException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class used for handling user {@link Rank ranks}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserRankHandler {

    private HashMap<String, ArrayList<Integer>> userRankIDs = new HashMap<>();
    private HashMap<String, ArrayList<Rank>> userRanks = new HashMap<>();
    private KohuCafeDatabaseConnection api;
    private SQLConnection connection;

    /**
     * Creates a new {@link UserRankHandler} object.
     * @param api The {@link KohuCafeDatabaseConnection api connection}.
     * @param connection The {@link SQLConnection database connection}.
     */
    public UserRankHandler(@NotNull KohuCafeDatabaseConnection api, @NotNull SQLConnection connection) {
        this.api = api;
        this.connection = connection;
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM user_ranks";

        for (SQLRow row : connection.getResultSet(query)) {
            String userID = row.getAsString("user_id");
            Integer rankID = row.getAsInteger("rank_id");

            if (!userRankIDs.containsKey(userID))
                userRankIDs.put(userID, new ArrayList<>());

            userRankIDs.get(userID).add(rankID);
        }

        userRankIDs.forEach((userID, rankIDs) -> {

            for (Integer rankID : rankIDs) {
                if (!userRanks.containsKey(userID))
                    userRanks.put(userID, new ArrayList<>());

                userRanks.get(userID).add(api.RANKS.getRank(rankID));
            }
        });
    }

    /**
     * Get an {@link ArrayList} containing all {@link Integer rank IDs} for a user.
     * @param userID The {@link String user ID} to check.
     * @return An {@link ArrayList} of {@link Integer rank IDs} for that user.
     * @throws UserDoesNotHaveRankException Thrown if the user does not have any {@link Rank ranks}.
     */
    @NotNull
    public ArrayList<Integer> getUserRankIDs(@NotNull String userID) throws UserDoesNotHaveRankException {
        if (!userRankIDs.containsKey(userID))
            throw new UserDoesNotHaveRanksException(userID);

        return userRankIDs.get(userID);
    }

    /**
     * Get an {@link ArrayList} containing all {@link Rank ranks} for a user.
     * @param userID The {@link String user ID} to check.
     * @return An {@link ArrayList} of {@link Rank ranks} for that user.
     * @throws UserDoesNotHaveRankException Thrown if the user does not have any {@link Rank ranks}.
     */
    @NotNull
    public ArrayList<Rank> getUserRanks(@NotNull String userID) throws UserDoesNotHaveRankException {
        if (!userRanks.containsKey(userID))
            throw new UserDoesNotHaveRanksException(userID);

        return userRanks.get(userID);
    }

    /**
     * Adds a new {@link Rank} to a {@link com.beanbeanjuice.tables.users.User User}.
     * @param userID The {@link String user ID} for that {@link com.beanbeanjuice.tables.users.User User}.
     * @param rankID The {@link Integer rank ID} for the {@link Rank}.
     * @return True, if the {@link Rank} was added successfully.
     * @throws RankDoesNotExistException Thrown if the {@link Rank} does not exist.
     * @throws UserDoesNotExistException Thrown if the {@link com.beanbeanjuice.tables.users.User User} does not exist.
     * @throws UserAlreadyHasRankException Thrown if the {@link com.beanbeanjuice.tables.users.User User} already has that {@link Rank}.
     */
    @NotNull
    public Boolean addRankToUser(@NotNull String userID, @NotNull Integer rankID)
            throws RankDoesNotExistException, UserDoesNotExistException, UserAlreadyHasRankException {
        // Check if the rank exists.
        if (!api.RANKS.rankExists(rankID))
            throw new RankDoesNotExistException(rankID);

        // Check if user exists.
        if (!api.USERS.userExists(userID))
            throw new UserDoesNotExistException(userID);

        // Check if the user already has the rank.
        if (userRankIDs.get(userID).contains(rankID))
            throw new UserAlreadyHasRankException(userID, rankID);

        String query = "INSERT INTO user_ranks VALUES (?, ?)";
        String[] values = new String[]{userID, rankID.toString()};

        if (connection.executeQuery(query, values)) {
            // Add it locally.
            addRank(userID, rankID);
            return true;
        }
        return false;
    }

    /**
     * Remove a {@link Rank} from a {@link com.beanbeanjuice.tables.users.User User}.
     * @param userID The {@link String user ID} for that {@link com.beanbeanjuice.tables.users.User User}.
     * @param rankID The {@link Integer rank ID} for that {@link Rank}.
     * @return True, if the {@link Rank} was successfully added to the {@link com.beanbeanjuice.tables.users.User User}.
     * @throws RankDoesNotExistException Thrown if the {@link Rank} does not exist.
     * @throws UserDoesNotExistException Thrown if the {@link com.beanbeanjuice.tables.users.User User} does not exist.
     * @throws UserDoesNotHaveRankException Thrown if the {@link com.beanbeanjuice.tables.users.User User} does not have that {@link Rank}.
     */
    @NotNull
    public Boolean removeRankFromUser(@NotNull String userID, @NotNull Integer rankID)
            throws RankDoesNotExistException, UserDoesNotExistException, UserDoesNotHaveRankException {
        // Check if the rank exists.
        if (!api.RANKS.rankExists(rankID))
            throw new RankDoesNotExistException(rankID);

        // Check if the user exists.
        if (!api.USERS.userExists(userID))
            throw new UserDoesNotExistException(userID);

        // Check if the user already has the rank.
        if (!api.USERS.getUser(userID).hasRank(rankID))
            throw new UserDoesNotHaveRankException(userID, rankID);

        String query = "DELETE FROM user_ranks WHERE user_id = (?) AND rank_id = (?)";
        String[] values = new String[]{userID, rankID.toString()};

        if (connection.executeQuery(query, values)) {
            // Update the Rank IDs locally
            api.USER_RANKS.removeRank(userID, rankID);
            return true;
        }
        return false;
    }

    private Boolean addRank(@NotNull String userID, @NotNull Integer rankID) {
        if (!userRankIDs.containsKey(userID))
            userRankIDs.put(userID, new ArrayList<>());

        if (userRankIDs.get(userID).contains(rankID))
            return false;
        userRankIDs.get(userID).add(rankID);
        userRanks.get(userID).add(api.RANKS.getRank(rankID));
        return true;
    }

    private Boolean removeRank(@NotNull String userID, @NotNull Integer rankID) {
        if (!userRankIDs.containsKey(userID))
            return true;

        int index;
        // Get the index of the rank.
        for (index = 0; index < userRankIDs.get(userID).size(); index++)
            if (Objects.equals(userRankIDs.get(userID).get(index), rankID))
                break;

        // Remove the index.
        userRankIDs.get(userID).remove(index);
        userRanks.get(userID).remove(api.RANKS.getRank(rankID));
        return true;
    }

    /**
     * @return A {@link HashMap} containing {@link String user ID keys} and a value of {@link ArrayList} of {@link Integer rank IDs}.
     */
    @NotNull
    public HashMap<String, ArrayList<Integer>> getUserRankIDsMap() {
        return userRankIDs;
    }

}
