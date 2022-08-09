package com.beanbeanjuice.tables.avatar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Avatar {

    private int maxHealth;
    private int currentHealth;
    private int strength;
    private int intelligence;
    private ArrayList<AvatarItem> items;

    public Avatar(@NotNull Integer maxHealth, @NotNull Integer strength, @NotNull Integer intelligence,
                  @NotNull ArrayList<AvatarItem> items) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.strength = strength;
        this.intelligence = intelligence;
        this.items = items;
    }

    @NotNull
    public Integer getMaxHealth() {
        return maxHealth;
    }

    @NotNull
    public Integer getCurrentHealth() {
        return currentHealth;  // TODO: In the future calculate this differently.
    }

    @NotNull
    public Integer getStrength() {
        return strength;
    }

    @NotNull
    public Integer getIntelligence() {
        return intelligence;
    }

    @NotNull
    public ArrayList<AvatarItem> getItems() {
        return items;
    }

    protected void copy(@NotNull Avatar avatar) {
        this.maxHealth = avatar.getMaxHealth();
        this.strength = avatar.getStrength();
        this.intelligence = avatar.getIntelligence();
    }

    protected void setMaxHealth(@NotNull Integer health) {
        this.maxHealth = health;
    }

    public void setCurrentHealth(@NotNull Integer health) {
        this.currentHealth = health;
    }

    protected void setStrength(@NotNull Integer strength) {
        this.strength = strength;
    }

    protected void setIntelligence(@NotNull Integer intelligence) {
        this.intelligence = intelligence;
    }

}
