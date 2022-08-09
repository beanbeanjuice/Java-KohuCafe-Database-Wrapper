package com.beanbeanjuice.tables.avatar;

import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.utility.exception.item.AvatarInventoryDoesNotContainItemException;
import com.beanbeanjuice.utility.exception.item.AvatarInventoryDoesNotExistException;
import com.beanbeanjuice.utility.exception.item.AvatarItemDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class used for {@link AvatarItem} handling.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class AvatarInventoryHandler {

    private HashMap<String, ArrayList<AvatarItem>> userItems = new HashMap<>();
    private KohuCafeAPI api;
    private SQLConnection connection;

    /**
     * Creates a new {@link AvatarInventoryHandler}.
     * @param api The {@link KohuCafeAPI api connection} to the database.
     * @param connection The {@link SQLConnection connection} to the database.
     */
    public AvatarInventoryHandler(@NotNull KohuCafeAPI api, @NotNull SQLConnection connection) {
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
            userItems.put(userID, new ArrayList<>());

        return userItems.get(userID);
    }

    @NotNull
    public AvatarItem getAvatarItem(@NotNull String userID, @NotNull Integer itemID)
    throws AvatarItemDoesNotExistException, AvatarInventoryDoesNotContainItemException {
        if (!userItems.containsKey(userID))
            throw new AvatarInventoryDoesNotExistException(userID);

        for (AvatarItem item : userItems.get(userID))
            if (item.getID().equals(itemID))
                return item;
        throw new AvatarInventoryDoesNotContainItemException(userID, itemID);
    }

    /**
     * Give an {@link AvatarItem} to a user.
     * @param userID The {@link String user ID} for that user.
     * @param itemID The {@link Integer item ID} specified.
     * @return True, if the {@link AvatarItem item} was given successfully.
     * @throws AvatarItemDoesNotExistException Thrown if the {@link AvatarItem item} does not exist.
     */
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

    /**
     * Remove an {@link AvatarItem item} from a specified user.
     * @param userID The {@link String user ID} specified.
     * @param itemID The {@link Integer item ID} specified.
     * @return True, if the {@link AvatarItem item} was removed successfully.
     * @throws AvatarItemDoesNotExistException Thrown if the {@link AvatarItem item} does not exist.
     */
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

    /**
     * Check if a user has an {@link AvatarItem item}.
     * @param userID The {@link String user ID} to check.
     * @param itemID The {@link Integer item ID}.
     * @return True, if the user has the {@link AvatarItem item}.
     */
    @NotNull
    public Boolean hasItem(@NotNull String userID, @NotNull Integer itemID) {
        if (!userItems.containsKey(userID))
            return false;

        for (AvatarItem item : userItems.get(userID))
            if (item.getID().equals(itemID))
                return true;
        return false;
    }

    /**
     * Count the amount of instances of a specified {@link AvatarItem item}.
     * @param itemID The {@link Integer item ID} specified.
     * @return The {@link Integer amount} of instances of that {@link AvatarItem item}.
     */
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
