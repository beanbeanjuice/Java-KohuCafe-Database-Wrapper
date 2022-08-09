package com.beanbeanjuice.tables.avatar;

import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.utility.exception.avatar.AvatarAlreadyExistsException;
import com.beanbeanjuice.utility.exception.avatar.AvatarDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLResult;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AvatarHandler {

    private HashMap<String, Avatar> avatars = new HashMap<>();
    private KohuCafeAPI api;
    private SQLConnection connection;

    public AvatarHandler(@NotNull KohuCafeAPI api, @NotNull SQLConnection connection) {
        this.api = api;
        this.connection = connection;
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM avatar_statistics";

        SQLResult result = connection.getResultSet(query);

        for (SQLRow row : result) {
            avatars.put(row.getAsString("user_id"), getAvatarFromRow(row));
        }
    }

    @NotNull
    public Boolean addAvatar(@NotNull String userID) throws AvatarAlreadyExistsException {

        if (avatars.containsKey(userID))
            throw new AvatarAlreadyExistsException(userID);

        String query = "INSERT INTO avatar_statistics (user_id) VALUES (?)";
        String[] values = new String[]{userID};

        if (connection.executeQuery(query, values)) {
            // Update locally.
            avatars.put(userID, getAvatarFromDatabase(userID));
            return true;
        }
        return false;
    }

    @NotNull
    private Avatar getAvatarFromDatabase(@NotNull String userID) {
        String query = "SELECT * FROM avatar_statistics WHERE user_id = (?)";
        String[] values = new String[]{userID};

        return getAvatarFromRow(connection.getResultSet(query, values).first());
    }

    @NotNull
    private Avatar getAvatarFromRow(@NotNull SQLRow row) {
        String userID = row.getAsString("user_id");
        Integer maxHealth = row.getAsInteger("max_health");
        Integer strength = row.getAsInteger("strength");
        Integer intelligence = row.getAsInteger("intelligence");

        return new Avatar(maxHealth, strength, intelligence, api.AVATAR_INVENTORY.getAvatarItems(userID));
    }

    @NotNull
    public Avatar getAvatar(@NotNull String userID) throws AvatarDoesNotExistException {
        if (!avatars.containsKey(userID))
            throw new AvatarDoesNotExistException(userID);

        return avatars.get(userID);
    }

    @NotNull
    public Boolean updateAvatar(@NotNull String userID, @NotNull Integer maxHealth, @NotNull Integer strength,
                                @NotNull Integer intelligence) throws AvatarDoesNotExistException {

        if (!avatars.containsKey(userID))
            throw new AvatarDoesNotExistException(userID);

        String query = "UPDATE avatar_statistics SET " +
                "max_health = (?), " +
                "strength = (?), " +
                "intelligence = (?) " +
                "WHERE user_id = (?)";

        String[] values = new String[]{
                maxHealth.toString(),
                strength.toString(),
                intelligence.toString(),
                userID
        };

        if (connection.executeQuery(query, values)) {
            // Update locally.
            avatars.get(userID).setMaxHealth(maxHealth);
            avatars.get(userID).setStrength(strength);
            avatars.get(userID).setIntelligence(intelligence);
            return true;
        }
        return false;
    }

}
