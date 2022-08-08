package com.beanbeanjuice.tables.avatar;

import org.jetbrains.annotations.NotNull;

public class AvatarItem {

    private final int ID;
    private String name;
    private String description;
    private String imageURL;
    private int total;
    private double weight;
    private double damage;

    public AvatarItem(@NotNull Integer id, @NotNull String name, @NotNull String description,
                      @NotNull String imageURL, @NotNull Integer total, @NotNull Double weight,
                      @NotNull Double damage) {
        this.ID = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.total = total;
        this.weight = weight;
        this.damage = damage;
    }

    @NotNull
    public Integer getID() {
        return ID;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    public String getImageURL() {
        return imageURL;
    }

    @NotNull
    public Integer getTotal() {
        return total;
    }

    @NotNull
    public Double getWeight() {
        return weight;
    }

    @NotNull
    public Double getDamage() {
        return damage;
    }

    protected void setValues(@NotNull AvatarItem item) {
        this.name = item.getName();
        this.description = item.getDescription();
        this.imageURL = item.getImageURL();
        this.total = item.getTotal();
        this.weight = item.getWeight();
        this.damage = item.getDamage();
    }

}
