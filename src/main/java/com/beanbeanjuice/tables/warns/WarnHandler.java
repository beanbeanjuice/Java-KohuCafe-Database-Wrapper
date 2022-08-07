package com.beanbeanjuice.tables.warns;

import com.beanbeanjuice.cafeapi.utility.Time;
import com.beanbeanjuice.utility.exception.WarnDoesNotExistException;
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

    @NotNull
    public ArrayList<Warn> getActiveUserWarns(@NotNull String userID) {
        if (!warns.containsKey(userID))
            return new ArrayList<>();

        ArrayList<Warn> activeWarns = new ArrayList<>();
        for (Warn warn : warns.get(userID))
            if (warn.isActive())
                activeWarns.add(warn);

        return activeWarns;
    }

    @NotNull
    public ArrayList<Warn> getAllUserWarns(@NotNull String userID) {
        if (!warns.containsKey(userID))
            return new ArrayList<>();

        return warns.get(userID);
    }

    @Nullable
    public Warn getWarn(@NotNull Integer warnID) throws WarnDoesNotExistException {
        for (Map.Entry<String, ArrayList<Warn>> pair : warns.entrySet()) {
            for (Warn warn : pair.getValue())
                if (warn.getWarnID().equals(warnID))
                    return warn;
        }
        throw new WarnDoesNotExistException();
    }

    @NotNull
    public HashMap<String, ArrayList<Warn>> getAllWarnsTable() {
        return warns;
    }

    @NotNull
    public ArrayList<Warn> getAllWarns() {
        ArrayList<Warn> warnsArray = new ArrayList<>();
        warns.forEach((userID, warns) -> {
            warnsArray.addAll(warns);
        });
        return warnsArray;
    }

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

    @NotNull
    public Boolean updateWarnActiveStatus(@NotNull Integer warnID, @NotNull Boolean isActive) {
        String query = "UPDATE warns SET active = (?) WHERE id = (?)";

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
