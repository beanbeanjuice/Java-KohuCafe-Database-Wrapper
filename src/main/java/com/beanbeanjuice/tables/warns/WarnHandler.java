package com.beanbeanjuice.tables.warns;

import com.beanbeanjuice.cafeapi.utility.Time;
import com.beanbeanjuice.utility.exception.WarnDoesNotExistException;
import com.beanbeanjuice.utility.exception.warn.UserDoesNotHaveActiveWarnsException;
import com.beanbeanjuice.utility.exception.warn.UserDoesNotHaveWarnsException;
import com.beanbeanjuice.utility.sql.SQLConnection;
import com.beanbeanjuice.utility.sql.SQLRow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class used for handling the {@link Warn} table in the database.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class WarnHandler {

    private HashMap<String, ArrayList<Warn>> warns = new HashMap<>();
    private SQLConnection connection;

    /**
     * Creates a new {@link WarnHandler} object.
     * @param connection The {@link SQLConnection database connection}.
     */
    public WarnHandler(@NotNull SQLConnection connection) {
        this.connection = connection;
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM warns";

        for (SQLRow column : connection.getResultSet(query)) {

            Integer warnID = column.getAsInteger("id");
            String userID = column.getAsString("user_id");
            String userNickname = column.getAsString("user_nickname");
            String issuerID = column.getAsString("issuer_id");
            String issuerNickname = column.getAsString("issuer_nickname");
            String date = column.getAsString("date");
            String reason = column.getAsString("reason");
            Boolean isActive = column.getAsBoolean("active");

            if (!warns.containsKey(userID))
                warns.put(userID, new ArrayList<>());

            warns.get(userID).add(new Warn(warnID, userID, userNickname, issuerID, issuerNickname, date, reason, isActive));
        }
    }

    /**
     * Get an {@link ArrayList} of all {@link Warn active warns} for a {@link com.beanbeanjuice.tables.users.User User}.
     * @param userID The {@link String user ID} to get the {@link Warn active warns} of.
     * @return An {@link ArrayList} of all {@link Warn active warns} for a {@link com.beanbeanjuice.tables.users.User User}.
     * @throws UserDoesNotHaveActiveWarnsException Thrown if the {@link com.beanbeanjuice.tables.users.User User} does not have any {@link Warn active warns}.
     */
    @NotNull
    public ArrayList<Warn> getActiveUserWarns(@NotNull String userID) throws UserDoesNotHaveActiveWarnsException {
        if (!warns.containsKey(userID))
            throw new UserDoesNotHaveActiveWarnsException(userID);

        ArrayList<Warn> activeWarns = new ArrayList<>();
        for (Warn warn : warns.get(userID))
            if (warn.isActive())
                activeWarns.add(warn);

        return activeWarns;
    }

    /**
     * Get an {@link ArrayList} of all {@link Warn warns} for a {@link com.beanbeanjuice.tables.users.User User}.
     * @param userID The {@link String user ID} to get the {@link Warn warns} for.
     * @return An {@link ArrayList} of all {@link Warn warns} for a {@link com.beanbeanjuice.tables.users.User User}.
     * @throws UserDoesNotHaveWarnsException Thrown if an {@link com.beanbeanjuice.tables.users.User User} does not have any {@link Warn warns}.
     */
    @NotNull
    public ArrayList<Warn> getAllUserWarns(@NotNull String userID) throws UserDoesNotHaveWarnsException {
        if (!warns.containsKey(userID))
            throw new UserDoesNotHaveWarnsException(userID);

        return warns.get(userID);
    }

    /**
     * Get a specific {@link Warn}.
     * @param warnID The {@link Integer warn ID} for the {@link Warn}.
     * @return The {@link Warn} specified.
     * @throws WarnDoesNotExistException Thrown if the {@link Warn} does not exist.
     */
    @NotNull
    public Warn getWarn(@NotNull Integer warnID) throws WarnDoesNotExistException {
        for (Map.Entry<String, ArrayList<Warn>> pair : warns.entrySet()) {
            for (Warn warn : pair.getValue())
                if (warn.getWarnID().equals(warnID))
                    return warn;
        }
        throw new WarnDoesNotExistException(warnID);
    }

    /**
     * @return A {@link HashMap} with keys of {@link String user IDs} and values of {@link ArrayList} of {@link Warn warns}.
     */
    @NotNull
    public HashMap<String, ArrayList<Warn>> getAllWarnsTable() {
        return warns;
    }

    /**
     * @return An {@link ArrayList} of all {@link Warn warns} in the database.
     */
    @NotNull
    public ArrayList<Warn> getAllWarns() {
        ArrayList<Warn> warnsArray = new ArrayList<>();
        warns.forEach((userID, warns) -> {
            warnsArray.addAll(warns);
        });
        return warnsArray;
    }

    /**
     * Add a {@link Warn} to the database.
     * @param userID The {@link String user ID} of the person the {@link Warn} is for.
     * @param userNickname The {@link String username} of the person the {@link Warn} is for.
     * @param issuerID The {@link String user ID} of the person who issued the {@link Warn}.
     * @param issuerNickname The {@link String nickname} of the person who issued the {@link Warn}.
     * @param reason The {@link String reason} for issuing the {@link Warn}.
     * @return True, if the {@link Warn} was added successfully.
     */
    @NotNull
    public Boolean addWarn(@NotNull String userID, @NotNull String userNickname,
                           @NotNull String issuerID, @NotNull String issuerNickname,
                           @NotNull String reason) {
        String dateString = new Time("UTC", "dd/MM/yyyy - HH:mm").format();

        String query = "INSERT INTO warns (user_id, user_nickname, issuer_id, issuer_nickname, date, reason) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String[] values = new String[]{
                userID,
                userNickname,
                issuerID,
                issuerNickname,
                dateString,
                reason
        };

        int newID = getAllWarns().size() + 1;

        Warn warn = new Warn(newID, userID, userNickname, issuerID, issuerNickname, dateString, reason, true);

        if (connection.executeQuery(query, values)) {
            if (!warns.containsKey(userID))
                warns.put(userID, new ArrayList<>());

            warns.get(userID).add(warn);
            return true;
        }
        return false;
    }

    /**
     * Check if a specific {@link Warn} exists.
     * @param warnID The {@link Integer warn ID} to check for.
     * @return True, if the {@link Warn} exists.
     */
    @NotNull
    public Boolean warnExists(@NotNull Integer warnID) {
        for (Map.Entry<String, ArrayList<Warn>> userWarns : warns.entrySet())
            for (Warn warn : userWarns.getValue())
                if (warn.getWarnID().equals(warnID))
                    return true;
        return false;
    }

    /**
     * Update the {@link Boolean active status} for a specific {@link Warn}.
     * @param warnID The {@link Integer warn ID} to update the {@link Boolean active status} for.
     * @param isActive The {@link Boolean active status} to set the {@link Warn} to.
     * @return True, if the {@link Boolean active status} was updated successfully.
     * @throws WarnDoesNotExistException Thrown if the {@link Warn} does not exist.
     */
    @NotNull
    public Boolean updateWarnActiveStatus(@NotNull Integer warnID, @NotNull Boolean isActive) throws WarnDoesNotExistException {
        String query = "UPDATE warns SET active = (?) WHERE id = (?)";

        // Check if the warn exists.
        if (!warnExists(warnID))
            throw new WarnDoesNotExistException(warnID);

        String[] values = new String[]{
                String.valueOf(isActive ? 1 : 0),
                warnID.toString()
        };

        if (connection.executeQuery(query, values)) {

            warns.forEach((user, warns) -> {
                for (Warn warn : warns)
                    if (warn.getWarnID().equals(warnID))
                        warn.changeActiveStatus(isActive);
            });

            return true;
        }
        return false;
    }

}
