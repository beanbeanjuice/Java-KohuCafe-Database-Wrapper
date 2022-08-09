package com.beanbeanjuice.tables.ranks;

import com.beanbeanjuice.utility.exception.rank.RankDoesNotExistException;
import com.beanbeanjuice.utility.sql.SQLRow;
import com.beanbeanjuice.utility.sql.SQLConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A class used for handling {@link Rank} objects.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class RankHandler {

    private ArrayList<Rank> ranks = new ArrayList<>();
    private SQLConnection connection;

    /**
     * Creates a new {@link RankHandler} object.
     * @param connection The {@link SQLConnection connection} to the database.
     */
    public RankHandler(@NotNull SQLConnection connection) {
        this.connection = connection;
        cache();
    }

    private void cache() {
        String query = "SELECT * FROM ranks";

        for (SQLRow column : connection.getResultSet(query)) {
            Integer id = column.getAsInteger("id");
            String roleID = column.getAsString("role_id");
            String name = column.getAsString("name");
            String description = column.getAsString("description");

            ranks.add(new Rank(id, roleID, name, description));
        }
    }

    /**
     * @return An {@link ArrayList} of all of the {@link Rank ranks}.
     */
    @Nullable
    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    /**
     * Retrieve a {@link Rank} from the database.
     * @param rankID The {@link Integer rank ID} of the {@link Rank}.
     * @return The {@link Rank} specified.
     * @throws RankDoesNotExistException Thrown when the {@link Integer rank ID} does not exist.
     */
    @NotNull
    public Rank getRank(@NotNull Integer rankID) throws RankDoesNotExistException {
        int randArrayIndex = rankID - 1;
        if (!rankExists(rankID))
            throw new RankDoesNotExistException(rankID);

        return ranks.get(randArrayIndex);
    }

    /**
     * Adds a {@link Rank} to the database.
     * @param name The {@link String name} of the {@link Rank}.
     * @param description The {@link String description} of the {@link Rank}.
     * @return True, if the {@link Rank} was added to the database successfully.
     */
    @NotNull
    public Boolean addRank(@NotNull String name, @NotNull String description) {
        return addRank("0", name, description);
    }

    /**
     * Adds a {@link Rank} to the database.
     * @param roleID The {@link String role ID} of the {@link Rank}.
     * @param name The {@link String name} of the {@link Rank}.
     * @param description The {@link String description} of the {@link Rank}.
     * @return True, if the {@link Rank} was added to the database successfully.
     */
    @NotNull
    public Boolean addRank(@NotNull String roleID, @NotNull String name, @NotNull String description) {
        String query = "INSERT INTO ranks (role_id, name, description) VALUES (?, ?, ?)";
        String[] values = new String[]{roleID, name, description};

        if (connection.executeQuery(query, values)) {
            // Update the rank locally.
            int rankID = ranks.size() + 1;

            ranks.add(new Rank(rankID, roleID, name, description));
            return true;
        }
        return false;
    }

    /**
     * Sets the {@link String role ID} of a specified {@link Rank}.
     * @param rankID The {@link Integer ID} of the {@link Rank}.
     * @param roleID The {@link String new role ID} of the {@link Rank}.
     * @return True, if the {@link String role ID} was updated successfully.
     * @throws RankDoesNotExistException Thrown if the {@link Rank} does not exist.
     */
    @NotNull
    public Boolean setRoleID(@NotNull Integer rankID, @NotNull String roleID) throws RankDoesNotExistException {
        int rankArrayIndex = rankID - 1;  // Index of arraylist

        if (!rankExists(rankID))
            throw new RankDoesNotExistException(rankID);

        String query = "UPDATE ranks SET role_id = (?) WHERE id = (?)";
        String[] values = new String[]{roleID, rankID.toString()};

        if (connection.executeQuery(query, values)) {
            ranks.get(rankArrayIndex).setRoleID(roleID);
            return true;
        }
        return false;
    }

    /**
     * Sets the {@link String name} of a specified {@link Rank}.
     * @param rankID The {@link Integer ID} of the {@link Rank}.
     * @param name The {@link String new name} of the {@link Rank}.
     * @return True, if the {@link String name} was updated successfully.
     * @throws RankDoesNotExistException Thrown if the {@link Rank} does not exist.
     */
    @NotNull
    public Boolean setName(@NotNull Integer rankID, @NotNull String name) throws RankDoesNotExistException {
        int rankArrayIndex = rankID - 1;  // Index of arraylist

        if (!rankExists(rankID))
            throw new RankDoesNotExistException(rankID);

        String query = "UPDATE ranks SET name = (?) WHERE id = (?)";
        String[] values = new String[]{name, rankID.toString()};

        if (connection.executeQuery(query, values)) {
            ranks.get(rankArrayIndex).setName(name);
            return true;
        }
        return false;
    }

    /**
     * Sets the {@link String description} of a specified {@link Rank}.
     * @param rankID The {@link Integer ID} of the {@link Rank}.
     * @param description The {@link String new description} of the {@link Rank}.
     * @return True, if the {@link String description} was updated successfully.
     * @throws RankDoesNotExistException Thrown if the {@link Rank} does not exist.
     */
    @NotNull
    public Boolean setDescription(@NotNull Integer rankID, @NotNull String description) throws RankDoesNotExistException {
        int rankArrayIndex = rankID - 1;  // Index of arraylist

        if (!rankExists(rankID))
            throw new RankDoesNotExistException(rankID);

        String query = "UPDATE ranks SET description = (?) WHERE id = (?)";
        String[] values = new String[]{description, rankID.toString()};

        if (connection.executeQuery(query, values)) {
            ranks.get(rankArrayIndex).setDescription(description);
            return true;
        }
        return false;
    }

    /**
     * Check if a {@link Rank} exists.
     * @param rankID The {@link Integer rank ID} to check.
     * @return True, if the {@link Rank} exists.
     */
    @NotNull
    public Boolean rankExists(@NotNull Integer rankID) {
        int rankArrayIndex = rankID - 1;
        return !(rankArrayIndex >= ranks.size() || rankArrayIndex < 0);
    }

}
