package com.beanbeanjuice.utility.exception.item;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link RuntimeException} used for when an {@link com.beanbeanjuice.tables.avatar.Avatar Avatar} inventory does not
 * contain a specified {@link com.beanbeanjuice.tables.avatar.AvatarItem item}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class AvatarInventoryDoesNotContainItemException extends RuntimeException {

    /**
     * Creates a new {@link AvatarInventoryDoesNotContainItemException} object.
     * @param userID The {@link String user ID}.
     * @param itemID The {@link Integer item ID}.
     */
    public AvatarInventoryDoesNotContainItemException(@NotNull String userID, @NotNull Integer itemID) {
        super("The avatar inventory for user (" + userID + ") does not contain the item (" + itemID + ").");
    }

}
