package com.beanbeanjuice.tables.users;

import com.beanbeanjuice.tables.ranks.Rank;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class User {

    private final String USER_ID;
    private Double balance;
    private Double multiplier;
    private ArrayList<Rank> ranks;

    public User(@NotNull String userID, @NotNull Double balance, @NotNull Double multiplier, @NotNull ArrayList<Rank> ranks) {
        this.USER_ID = userID;
        this.balance = balance;
        this.multiplier = multiplier;
        this.ranks = ranks;
    }

    @NotNull
    public String getUserID() {
        return USER_ID;
    }

    @NotNull
    public Double getBalance() {
        return balance;
    }

    @NotNull
    public Double getMultiplier() {
        return multiplier;
    }

    @NotNull
    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    public void updateBalance(@NotNull Double balance) {
        this.balance = balance;
        // TODO: Update in database/cache.
    }

    public void updateMultiplier(@NotNull Double multiplier) {
        this.multiplier = multiplier;
        // TODO: Update in database/cache.
    }

    public void addRank(@NotNull Rank rank) {
        ranks.add(rank);
        // TODO: Logic to check if already contains rank.
        // TODO: Update in database/cache.
    }

    public void removeRank(@NotNull Rank rank) {
        ranks.remove(rank);
        // TODO: Logic to check if already contains rank.
        // TODO: Update in database/cache.
    }


}
