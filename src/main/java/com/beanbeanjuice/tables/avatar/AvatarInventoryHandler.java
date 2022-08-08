package com.beanbeanjuice.tables.avatar;

import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.utility.exception.item.AvatarItemDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AvatarInventoryHandler {

    private HashMap<String, ArrayList<AvatarItem>> userItems = new HashMap<>();
    private KohuCafeDatabaseConnection api;
    private SQLConnection connection;

    public AvatarInventoryHandler(@NotNull KohuCafeDatabaseConnection api, @NotNull SQLConnection connection) {
        this.api = api;
        this.connection = connection;
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM avatar_inventories";

        for (SQLRow row : connection.getResultSet(query)) {
            String userID = row.getAsString("user_id");
            int itemID = row.getAsInteger("id");

            if (!userItems.containsKey(userID))
                userItems.put(userID, new ArrayList<>());

            userItems.get(userID).add(api.AVATAR_ITEMS.getItem(itemID));  // TODO: Test if this updates for all items.
        }
    }

    @NotNull
    public ArrayList<AvatarItem> getAvatarItems(@NotNull String userID) {
        if (!userItems.containsKey(userID))
            return new ArrayList<>();

        return userItems.get(userID);
    }

    @Nullable
    public AvatarItem getAvatarItem(@NotNull String userID, @NotNull Integer itemID) {
        if (!userItems.containsKey(userID))
            return null;

        for (AvatarItem item : userItems.get(userID))
            if (item.getID().equals(itemID))
                return item;
        return null;
    }

    @NotNull
    public Boolean giveItem(@NotNull String userID, @NotNull Integer itemID) throws AvatarItemDoesNotExistException {
        // Check if item exists.
        if (!api.AVATAR_ITEMS.checkIfItemExists(itemID))
            throw new AvatarItemDoesNotExistException(itemID);

        // Check if the user can have the item.
        if (countItems(itemID) >= api.AVATAR_ITEMS.getItem(itemID).getTotal())
            return false;

        // Update in database.
        String query = "INSERT INTO avatar_inventories VALUES (?, ?)";
        String[] values = new String[]{userID, itemID.toString()};

        if (connection.executeQuery(query, values)) {
            // Update locally.
            if (!userItems.containsKey(userID))
                userItems.put(userID, new ArrayList<>());

            userItems.get(userID).add(api.AVATAR_ITEMS.getItem(itemID));
            return true;
        }
        return false;
    }

    @NotNull
    public Boolean removeItem(@NotNull String userID, @NotNull Integer itemID) throws AvatarItemDoesNotExistException {
        // Check if item exists.
        if (!api.AVATAR_ITEMS.checkIfItemExists(itemID))
            throw new AvatarItemDoesNotExistException(itemID);

        // Check if the user has the item.
        if (!hasItem(userID, itemID))
            return false;

        String query = "DELETE FROM avatar_inventories WHERE user_id = (?) AND id = (?)";
        String[] values = new String[]{userID, itemID.toString()};

        if (connection.executeQuery(query, values)) {
            // Remove locally.
            userItems.get(userID).remove(api.AVATAR_ITEMS.getItem(itemID));
            return true;
        }
        return false;
    }

    @NotNull
    public Boolean hasItem(@NotNull String userID, @NotNull Integer itemID) {
        if (!userItems.containsKey(userID))
            return false;

        for (AvatarItem item : userItems.get(userID))
            if (item.getID().equals(itemID))
                return true;
        return false;
    }

    @NotNull
    public Integer countItems(@NotNull Integer itemID) {
        int num = 0;

        for (Map.Entry<String, ArrayList<AvatarItem>> pair : userItems.entrySet())
            for (AvatarItem item : pair.getValue())
                if (item.getID().equals(itemID))
                    num++;

        return num;
    }

}
