package com.beanbeanjuice.tables.ranks;

import com.beanbeanjuice.utility.sql.SQLRow;
import com.beanbeanjuice.utility.sql.SQLConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class RankHandler {

    private HashMap<String, ArrayList<Rank>> rankMap = new HashMap<>();
    private SQLConnection connection;

    public RankHandler(@NotNull SQLConnection connection) {
        this.connection = connection;
        startCache();
    }

    private void startCache() {
        String query = "SELECT * FROM user_ranks";

        for (SQLRow column : connection.runStatement(query)) {
            String userID = column.getAsString("user_id");
            String name = column.getAsString("rank_name");
            String description = column.getAsString("rank_description");

            if (!rankMap.containsKey(userID))
                rankMap.put(userID, new ArrayList<>());

            rankMap.get(userID).add(new Rank(name, description));
        }
    }

    @Nullable
    public ArrayList<Rank> getUserRanks(@NotNull String userID) {
        if (!rankMap.containsKey(userID))
            return null;

        return rankMap.get(userID);
    }

}
