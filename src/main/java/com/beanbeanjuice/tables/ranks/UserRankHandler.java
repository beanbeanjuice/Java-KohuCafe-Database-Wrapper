package com.beanbeanjuice.tables.ranks;

import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class used for handling user {@link Rank ranks}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class UserRankHandler {

    private HashMap<String, ArrayList<Integer>> userRanks = new HashMap<>();
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

            if (!userRanks.containsKey(userID))
                userRanks.put(userID, new ArrayList<>());

            userRanks.get(userID).add(rankID);
        }
    }

    /**
     * Get all of the {@link Integer rank IDs} for a specified user.
     * @param userID The {@link String user ID}.
     * @return An {@link ArrayList} of {@link Integer rank IDs} for that user.
     */
    @NotNull
    public ArrayList<Integer> getUserRankIDs(@NotNull String userID) {
        if (!userRanks.containsKey(userID))
            return new ArrayList<>();

        return userRanks.get(userID);
    }

    /**
     * Get all of the {@link Rank ranks} for a specified user.
     * @param userID The {@link String user ID}.
     * @return An {@link ArrayList} of {@link Rank ranks} for that user.
     */
    @NotNull
    public ArrayList<Rank> getUserRanks(@NotNull String userID) {
        ArrayList<Rank> ranks = new ArrayList<>();
        for (Integer i : userRanks.get(userID))
            ranks.add(api.RANKS.getRank(i));

        return ranks;
    }

    /**
     * @return A {@link HashMap} containing {@link String user ID keys} and a value of {@link ArrayList} of {@link Integer rank IDs}.
     */
    @NotNull
    public HashMap<String, ArrayList<Integer>> getUserRanksMap() {
        return userRanks;
    }

}
