package com.beanbeanjuice.tables.avatar;

import org.jetbrains.annotations.NotNull;

/**
 * A class used for {@link AvatarItem}.
 */
public class AvatarItem {

    private final int ID;
    private String name;
    private String description;
    private String imageURL;
    private int total;
    private double weight;
    private double damage;

    /**
     * Creates a new {@link AvatarItem} object.
     * @param id The {@link Integer ID} of the {@link AvatarItem item}.
     * @param name The {@link String name} of the {@link AvatarItem item}.
     * @param description The {@link String description} of the {@link AvatarItem item}.
     * @param imageURL The {@link String image URL} of the {@link AvatarItem item}.
     * @param total The {@link Integer total} of the {@link AvatarItem item}.
     * @param weight The {@link Double weight} of the {@link AvatarItem item}.
     * @param damage The {@link Double damage} of the {@link AvatarItem item}.
     */
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

    /**
     * @return The {@link Integer item ID}.
     */
    @NotNull
    public Integer getID() {
        return ID;
    }

    /**
     * @return The {@link String item name}.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return The {@link String item description}.
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * @return The {@link String image URL}.
     */
    @NotNull
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @return The {@link Integer item total}.
     */
    @NotNull
    public Integer getTotal() {
        return total;
    }

    /**
     * @return The {@link Double weight}.
     */
    @NotNull
    public Double getWeight() {
        return weight;
    }

    /**
     * @return The {@link Double damage}.
     */
    @NotNull
    public Double getDamage() {
        return damage;
    }

    /**
     * A copy constructor for {@link AvatarItem}.
     * @param item The {@link AvatarItem item} to copy the values for.
     */
    protected void setValues(@NotNull AvatarItem item) {
        this.name = item.getName();
        this.description = item.getDescription();
        this.imageURL = item.getImageURL();
        this.total = item.getTotal();
        this.weight = item.getWeight();
        this.damage = item.getDamage();
    }

}
