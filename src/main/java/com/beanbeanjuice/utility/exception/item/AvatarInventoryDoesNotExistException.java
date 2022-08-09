package com.beanbeanjuice.utility.exception.item;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link RuntimeException} used for when a user does not have an {@link com.beanbeanjuice.tables.avatar.Avatar Avatar} inventory.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class AvatarInventoryDoesNotExistException extends RuntimeException {

    /**
     * Creates a new {@link AvatarInventoryDoesNotExistException} object.
     * @param userID The {@link String user ID}.
     */
    public AvatarInventoryDoesNotExistException(@NotNull String userID) {
        super("The avatar inventory for user (" + userID + ") does not exist.");
    }

}
