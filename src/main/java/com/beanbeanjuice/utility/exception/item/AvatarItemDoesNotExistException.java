package com.beanbeanjuice.utility.exception.item;

import org.jetbrains.annotations.NotNull;

/**
 * A class used for when an {@link com.beanbeanjuice.tables.avatar.AvatarItem AvatarItem} does not exist.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class AvatarItemDoesNotExistException extends RuntimeException {

    /**
     * Creates a new {@link AvatarItemDoesNotExistException}.
     * @param itemID The {@link Integer ID} of the {@link com.beanbeanjuice.tables.avatar.AvatarItem AvatarItem} that does not exist.
     */
    public AvatarItemDoesNotExistException(@NotNull Integer itemID) {
        super("The item (" + itemID + ") does not exist.");
    }

}
