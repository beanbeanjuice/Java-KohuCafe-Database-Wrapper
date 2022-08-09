import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.utility.exception.rank.RankDoesNotExistException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestRankHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");
    private static final String OWNER = "690927484199370753";

    @Test
    @DisplayName("Test Rank Handler")
    public void testRankHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeDatabaseConnection.USER_RANKS.getUserRanks(OWNER));
        int currentID = kohuCafeDatabaseConnection.RANKS.getRanks().size() + 1;
        String roleID = String.valueOf(currentID + 500);

        Assertions.assertTrue(kohuCafeDatabaseConnection.RANKS.addRank(roleID, "For Testing", "For testing purposes."));

        Assertions.assertNotNull(kohuCafeDatabaseConnection.RANKS.getRank(currentID));
        Assertions.assertEquals(roleID, kohuCafeDatabaseConnection.RANKS.getRank(currentID).getRoleID());
        Assertions.assertEquals("For Testing", kohuCafeDatabaseConnection.RANKS.getRank(currentID).getName());
        Assertions.assertEquals("For testing purposes.", kohuCafeDatabaseConnection.RANKS.getRank(currentID).getDescription());

        String newRoleID = String.valueOf(currentID + 499);

        Assertions.assertTrue(kohuCafeDatabaseConnection.RANKS.setRoleID(currentID, newRoleID));
        Assertions.assertEquals(newRoleID, kohuCafeDatabaseConnection.RANKS.getRank(currentID).getRoleID());

        Assertions.assertTrue(kohuCafeDatabaseConnection.RANKS.setName(currentID, "For Not Testing"));
        Assertions.assertEquals("For Not Testing", kohuCafeDatabaseConnection.RANKS.getRank(currentID).getName());

        Assertions.assertTrue(kohuCafeDatabaseConnection.RANKS.setDescription(currentID, "For not testing purposes."));
        Assertions.assertEquals("For not testing purposes.", kohuCafeDatabaseConnection.RANKS.getRank(currentID).getDescription());
    }

    @Test
    @DisplayName("Test Rank Handler Exceptions")
    public void testRankHandlerExceptions() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        int currentID = kohuCafeDatabaseConnection.RANKS.getRanks().size() + 1;
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.getRank(currentID + 10));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.getRank(-1));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.setRoleID(currentID + 10, "0"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.setRoleID(-1, "0"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.setName(currentID + 10, "Fail"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.setName(-1, "Fail"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.setDescription(currentID + 10, "Fail"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeDatabaseConnection.RANKS.setDescription(-1, "Fail"));

    }

}
