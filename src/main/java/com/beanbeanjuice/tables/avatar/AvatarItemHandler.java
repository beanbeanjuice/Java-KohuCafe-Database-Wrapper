package com.beanbeanjuice.tables.avatar;

import com.beanbeanjuice.utility.exception.item.AvatarItemDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AvatarItemHandler {

    private ArrayList<AvatarItem> items = new ArrayList<>();
    private SQLConnection connection;

    public AvatarItemHandler(@NotNull SQLConnection connection) {
        this.connection = connection;
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM avatar_items";

        for (SQLRow row : connection.getResultSet(query)) {
            int id = row.getAsInteger("id");
            String name = row.getAsString("name");
            String description = row.getAsString("description");
            String imageURL = row.getAsString("image_url");
            int total = row.getAsInteger("total");
            double weight = row.getAsDouble("weight");
            double damage = row.getAsDouble("damage");

            items.add(new AvatarItem(id, name, description, imageURL, total, weight, damage));
        }
    }

    @NotNull
    public ArrayList<AvatarItem> getItems() {
        return items;
    }

    @NotNull
    public AvatarItem getItem(@NotNull Integer id) throws AvatarItemDoesNotExistException {
        int arrayIndex = id - 1;  // Arraylist Indexing

        // Checking if the item exists.
        if (arrayIndex < 0 || arrayIndex >= items.size())
            throw new AvatarItemDoesNotExistException(id);

        return items.get(arrayIndex);
    }

    @NotNull
    public Boolean checkIfItemExists(@NotNull Integer id) {
        int arrayIndex = id - 1;
        return arrayIndex >= 0 && arrayIndex < items.size();
    }

    @NotNull
    public Boolean addNewItem(@NotNull String name, @NotNull String description, @NotNull String imageURL,
                              @NotNull Integer total, @NotNull Double weight, @NotNull Double damage) {
        String query = "INSERT INTO avatar_items (name, description, image_url, total, weight, damage) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String[] values = new String[]{name, description, imageURL, total.toString(), weight.toString(), damage.toString()};

        if (connection.executeQuery(query, values)) {
            // If it added it to the database, add it to the local cache.
            int newItemID = items.size() + 1;
            items.add(new AvatarItem(newItemID, name, description, imageURL, total, weight, damage));
            return true;
        }
        return false;
    }

    @NotNull
    public Boolean updateItem(@NotNull AvatarItem item) {
        // Check if the item exists.
        int arrayIndex = item.getID() - 1;
        if (arrayIndex < 0 || arrayIndex >= items.size())
            throw new AvatarItemDoesNotExistException(item.getID());

        String query = "UPDATE avatar_items SET " +
                "name = (?), " +
                "description = (?), " +
                "image_url = (?), " +
                "total = (?), " +
                "weight = (?), " +
                "damage = (?) " +
                "WHERE id = (?)";

        String[] values = new String[]{
                item.getName(),
                item.getDescription(),
                item.getImageURL(),
                item.getTotal().toString(),
                item.getWeight().toString(),
                item.getDamage().toString(),
                item.getID().toString()
        };

        if (connection.executeQuery(query, values)) {
            // Update locally.
            getItem(item.getID()).setValues(item);
            return true;
        }
        return false;
    }

}
