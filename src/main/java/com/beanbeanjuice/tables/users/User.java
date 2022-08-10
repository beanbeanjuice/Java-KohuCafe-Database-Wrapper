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
    private int experience;
    private double balance;
    private double multiplier;
    private ArrayList<Rank> ranks;

    /**
     * Creates a new {@link User} object.
     * @param userID The {@link String user ID} for that {@link User}.
     * @param experience The {@link Integer experience} for that {@link User}.
     * @param balance The {@link Double balance} for that {@link User}.
     * @param multiplier The {@link Double multiplier} for that {@link User}.
     * @param ranks The {@link ArrayList} of {@link Rank ranks} for that {@link User}.
     */
    public User(@NotNull String userID, @NotNull Integer experience, @NotNull Double balance,
                @NotNull Double multiplier, @NotNull ArrayList<Rank> ranks) {
        this.USER_ID = userID;
        this.experience = experience;
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

    @NotNull
    public Integer getExperience() {
        return experience;
    }

    @NotNull
    public Integer getLevel() {
        // 100 Experience - Level 1
        // 1000 Experience - Level 2
        // 10000 Experience - Level 3
        int levelExperience = 100;
        int currentLevel = 0;
        while (experience > levelExperience) {
            currentLevel++;
            levelExperience *= 10;
        }
        return currentLevel;
    }

    @NotNull
    public Integer getExperienceToNextLevel() {
        int level = getLevel();
        int levelExperience = 100;
        for (int i = 0; i < level; i++)
            levelExperience *= 10;
        return levelExperience - experience;
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

    protected void addExperience(@NotNull Integer experience) {
        this.experience += experience;
    }

    /**
     * Update the {@link Double balance} for the {@link User user}.
     * @param balance The new {@link Double balance} to be set to.
     */
    protected void updateBalance(@NotNull Double balance) {
        this.balance = balance;
    }

    /**
     * Update the {@link Double multiplier} for the {@link User user}.
     * @param multiplier The new {@link Double multiplier} to be set to.
     */
    protected void updateMultiplier(@NotNull Double multiplier) {
        this.multiplier = multiplier;
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

}
