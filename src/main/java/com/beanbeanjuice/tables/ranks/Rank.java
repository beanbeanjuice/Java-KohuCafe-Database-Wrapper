package com.beanbeanjuice.tables.ranks;

import org.jetbrains.annotations.NotNull;

public class Rank {

    private String name;
    private String description;

    public Rank(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
        // TODO: Update this in the database.
    }

    public void setDescription(String description) {
        this.description = description;
        // TODO: Update this in the database.
    }
}
