package com.beanbeanjuice.tables.ranks;

import org.jetbrains.annotations.NotNull;

/**
 * A class used to hold user {@link Rank} information.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class Rank {

    private final int ID;
    private String roleID;
    private String name;
    private String description;

    /**
     * Creates a new {@link Rank} object.
     * @param id The {@link Integer id} of the {@link Rank}.
     * @param roleID The {@link String role ID} of the {@link Rank}.
     * @param name The {@link String name} of the {@link Rank}.
     * @param description The {@link String description} of the {@link Rank}.
     */
    public Rank(@NotNull Integer id, @NotNull String roleID, @NotNull String name, @NotNull String description) {
        this.ID = id;
        this.roleID = roleID;
        this.name = name;
        this.description = description;
    }

    /**
     * @return The {@link Integer id} of the {@link Rank}.
     */
    @NotNull
    public Integer getID() {
        return ID;
    }

    /**
     * @return The {@link String role ID} of the {@link Rank}.
     */
    @NotNull
    public String getRoleID() {
        return roleID;
    }

    /**
     * @return The {@link String name} of the {@link Rank}.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return The {@link String description} of the {@link Rank}.
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    protected void setRoleID(@NotNull String roleID) {
        this.roleID = roleID;
    }

    protected void setName(@NotNull String name) {
        this.name = name;
    }

    protected void setDescription(@NotNull String description) {
        this.description = description;
    }
}
