package com.beanbeanjuice.tables.avatar;

import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.utility.exception.avatar.AvatarAlreadyExistsException;
import com.beanbeanjuice.utility.exception.avatar.AvatarDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLResult;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * A class used for handling {@link Avatar avatars}.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 * @version 1.1.0
 */
public class AvatarHandler {

    private HashMap<String, Avatar> avatars = new HashMap<>();
    private KohuCafeAPI api;
    private SQLConnection connection;

    /**
     * Creates a nwe {@link AvatarHandler} object.
     * @param api The {@link KohuCafeAPI api connection}.
     * @param connection The {@link SQLConnection database connection}.
     */
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

    /**
     * Adds an {@link Avatar} to the database.
     * @param userID The {@link String user ID} of the {@link Avatar} to add.
     * @return True, if the {@link Avatar} was added successfully.
     * @throws AvatarAlreadyExistsException Thrown if an {@link Avatar} with that {@link String user ID} already exists.
     */
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
        Integer experience = row.getAsInteger("experience");
        Integer maxHealth = row.getAsInteger("max_health");
        Integer strength = row.getAsInteger("strength");
        Integer intelligence = row.getAsInteger("intelligence");

        return new Avatar(maxHealth, experience, strength, intelligence, api.AVATAR_INVENTORY.getAvatarItems(userID));
    }

    /**
     * Get an {@link Avatar} from a specified {@link String user ID}.
     * @param userID The {@link String user ID} specified.
     * @return The specified {@link Avatar}.
     * @throws AvatarDoesNotExistException Thrown if an {@link Avatar} for that {@link String user ID} does not exist.
     */
    @NotNull
    public Avatar getAvatar(@NotNull String userID) throws AvatarDoesNotExistException {
        if (!avatars.containsKey(userID))
            throw new AvatarDoesNotExistException(userID);

        return avatars.get(userID);
    }

    /**
     * Add experience to an {@link Avatar} in the database.
     * @param userID The {@link String user ID}.
     * @param experience The {@link Integer experience} to add.
     * @return True, if the {@link Integer experience} was added successfully.
     * @throws AvatarDoesNotExistException Thrown if the {@link Avatar} does not exist.
     */
    @NotNull
    public Boolean addExperience(@NotNull String userID, @NotNull Integer experience) throws AvatarDoesNotExistException {
        if (!avatars.containsKey(userID))
            throw new AvatarDoesNotExistException(userID);

        String query = "UPDATE avatar_statistics SET experience = experience + (?) WHERE user_id = (?)";
        String[] values = new String[]{experience.toString(), userID};

        if (connection.executeQuery(query, values)) {
            // Update locally.
            avatars.get(userID).addExperience(experience);
            return true;
        }
        return false;
    }

    /**
     * Update an {@link Avatar} in the database.
     * @param userID The {@link String user ID} of the {@link Avatar}.
     * @param maxHealth The {@link Integer max health} of the {@link Avatar}.
     * @param strength The {@link Integer strength} of the {@link Avatar}.
     * @param intelligence The {@link Integer intelligence} of the {@link Avatar}.
     * @return True, if the {@link Avatar} was updated successfully.
     * @throws AvatarDoesNotExistException Thrown if the {@link Avatar} does not exist for that {@link String user ID}.
     */
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
