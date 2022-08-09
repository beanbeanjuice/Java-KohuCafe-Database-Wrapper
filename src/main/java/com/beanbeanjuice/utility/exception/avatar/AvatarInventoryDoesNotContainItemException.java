package com.beanbeanjuice.utility.exception.avatar;

import org.jetbrains.annotations.NotNull;

public class AvatarInventoryDoesNotContainItemException extends RuntimeException {

    public AvatarInventoryDoesNotContainItemException(@NotNull String userID, @NotNull Integer itemID) {
        super("The avatar inventory for user (" + userID + ") does not contain the item (" + itemID + ").");
    }

}
