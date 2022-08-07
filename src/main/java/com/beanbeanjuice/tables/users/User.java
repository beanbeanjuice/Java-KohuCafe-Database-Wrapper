package com.beanbeanjuice.tables.users;

import com.beanbeanjuice.tables.ranks.Rank;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A class used for holding Discord {@link User} information.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class User {

    private final String USER_ID;
    private Double balance;
    private Double multiplier;
    private ArrayList<Rank> ranks;

    /**
     * Creates a new {@link User} object.
     * @param userID The {@link String user ID} for that {@link User}.
     * @param balance The {@link Double balance} for that {@link User}.
     * @param multiplier The {@link Double multiplier} for that {@link User}.
     * @param ranks The {@link ArrayList} of {@link Rank ranks} for that {@link User}.
     */
    public User(@NotNull String userID, @NotNull Double balance, @NotNull Double multiplier, @NotNull ArrayList<Rank> ranks) {
        this.USER_ID = userID;
        this.balance = balance;
        this.multiplier = multiplier;
        this.ranks = ranks;
    }

    /**
     * @return The {@link String user ID} for the {@link User}.
     */
    @NotNull
    public String getUserID() {
        return USER_ID;
    }

    /**
     * @return The {@link Double balance} for that {@link User}.
     */
    @NotNull
    public Double getBalance() {
        return balance;
    }

    /**
     * @return The {@link Double multiplier} for that {@link User}.
     */
    @NotNull
    public Double getMultiplier() {
        return multiplier;
    }

    /**
     * @return The {@link ArrayList} of {@link Rank ranks} for that {@link User}.
     */
    @NotNull
    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    protected void updateBalance(@NotNull Double balance) {
        this.balance = balance;
    }

    protected void updateMultiplier(@NotNull Double multiplier) {
        this.multiplier = multiplier;
    }

    protected void addRank(@NotNull Rank rank) {
        ranks.add(rank);
        // TODO: Logic to check if already contains rank.
        // TODO: Update in database/cache.
    }

    /**
     * Check if the {@link User} has a specified {@link Integer rank ID}.
     * @param rankID The {@link Integer rank ID} to check.
     * @return True, if the user has that specified {@link Integer rank ID}.
     */
    @NotNull
    public Boolean hasRank(@NotNull Integer rankID) {
        for (Rank rank : ranks)
            if (rank.getID().equals(rankID))
                return true;
        return false;
    }

    protected void removeRank(@NotNull Rank rank) {
        ranks.remove(rank);
        // TODO: Logic to check if already contains rank.
        // TODO: Update in database/cache.
    }


}
