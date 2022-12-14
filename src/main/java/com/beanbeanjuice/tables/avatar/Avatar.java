package com.beanbeanjuice.tables.avatar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A class used for {@link Avatar} information.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 * @version 1.1.0
 */
public class Avatar {

    private int maxHealth;
    private int experience;
    private int currentHealth;
    private int strength;
    private int intelligence;
    private ArrayList<AvatarItem> items;

    /**
     * Creates a new {@link Avatar}.
     * @param maxHealth The {@link Integer max health} for the {@link Avatar}.
     * @param experience The {@link Integer experience} for the {@link Avatar}.
     * @param strength The {@link Integer strength} of the {@link Avatar}.
     * @param intelligence The {@link Integer intelligence} of the {@link Avatar}.
     * @param items The {@link ArrayList} of {@link AvatarItem items} for the {@link Avatar}.
     */
    public Avatar(@NotNull Integer maxHealth, @NotNull Integer experience, @NotNull Integer strength,
                  @NotNull Integer intelligence, @NotNull ArrayList<AvatarItem> items) {
        this.maxHealth = maxHealth;
        this.experience = experience;
        this.currentHealth = maxHealth;
        this.strength = strength;
        this.intelligence = intelligence;
        this.items = items;
    }

    /**
     * @return The total {@link Integer experience} for the {@link Avatar}.
     */
    @NotNull
    public Integer getExperience() {
        return experience;
    }

    /**
     * @return The current {@link Integer level} for the {@link Avatar}.
     */
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

    /**
     * @return The amount of {@link Integer experience} needed for the next level.
     */
    @NotNull
    public Integer getExperienceToNextLevel() {
        int level = getLevel();
        int levelExperience = 100;
        for (int i = 0; i < level; i++)
            levelExperience *= 10;
        return levelExperience - experience;
    }

    /**
     * @return The {@link Integer max health} of the {@link Avatar}.
     */
    @NotNull
    public Integer getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return The {@link Integer current health} of the {@link Avatar}.
     */
    @NotNull
    public Integer getCurrentHealth() {
        return currentHealth;
    }

    /**
     * @return The {@link Integer strength} of the {@link Avatar}.
     */
    @NotNull
    public Integer getStrength() {
        return strength;
    }

    /**
     * @return The {@link Integer intelligence} of the {@link Avatar}.
     */
    @NotNull
    public Integer getIntelligence() {
        return intelligence;
    }

    /**
     * @return The {@link ArrayList} of {@link AvatarItem items} of the {@link Avatar}.
     */
    @NotNull
    public ArrayList<AvatarItem> getItems() {
        return items;
    }

    /**
     * Adds {@link Integer experience} to the {@link Avatar}.
     * @param experience The {@link Integer experience} to add.
     */
    protected void addExperience(@NotNull Integer experience) {
        this.experience += experience;
    }

    /**
     * Sets the {@link Integer max health} of the {@link Avatar}.
     * @param health The {@link Integer health} to set the {@link Integer max health} to.
     */
    protected void setMaxHealth(@NotNull Integer health) {
        this.maxHealth = health;
    }

    /**
     * Sets the {@link Integer current health} of the {@link Avatar}.
     * @param health The {@link Integer health} to set the {@link Integer current health} to.
     */
    public void setCurrentHealth(@NotNull Integer health) {
        this.currentHealth = health;
    }

    /**
     * Sets the {@link Integer strength} of the {@link Avatar}.
     * @param strength The {@link Integer strength} to set the {@link Integer current strength} to.
     */
    protected void setStrength(@NotNull Integer strength) {
        this.strength = strength;
    }

    /**
     * Sets the {@link Integer intelligence} of the {@link Avatar}.
     * @param intelligence The {@link Integer intelligence} to set the {@link Integer current intelligence} to.
     */
    protected void setIntelligence(@NotNull Integer intelligence) {
        this.intelligence = intelligence;
    }

}
