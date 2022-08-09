import com.beanbeanjuice.KohuCafeAPI;
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
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeAPI.USER_RANKS.getUserRanks(OWNER));
        int currentID = kohuCafeAPI.RANKS.getRanks().size() + 1;
        String roleID = String.valueOf(currentID + 500);

        Assertions.assertTrue(kohuCafeAPI.RANKS.addRank(roleID, "For Testing", "For testing purposes."));

        Assertions.assertNotNull(kohuCafeAPI.RANKS.getRank(currentID));
        Assertions.assertEquals(roleID, kohuCafeAPI.RANKS.getRank(currentID).getRoleID());
        Assertions.assertEquals("For Testing", kohuCafeAPI.RANKS.getRank(currentID).getName());
        Assertions.assertEquals("For testing purposes.", kohuCafeAPI.RANKS.getRank(currentID).getDescription());

        String newRoleID = String.valueOf(currentID + 499);

        Assertions.assertTrue(kohuCafeAPI.RANKS.setRoleID(currentID, newRoleID));
        Assertions.assertEquals(newRoleID, kohuCafeAPI.RANKS.getRank(currentID).getRoleID());

        Assertions.assertTrue(kohuCafeAPI.RANKS.setName(currentID, "For Not Testing"));
        Assertions.assertEquals("For Not Testing", kohuCafeAPI.RANKS.getRank(currentID).getName());

        Assertions.assertTrue(kohuCafeAPI.RANKS.setDescription(currentID, "For not testing purposes."));
        Assertions.assertEquals("For not testing purposes.", kohuCafeAPI.RANKS.getRank(currentID).getDescription());
    }

    @Test
    @DisplayName("Test Rank Handler Exceptions")
    public void testRankHandlerExceptions() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);
        int currentID = kohuCafeAPI.RANKS.getRanks().size() + 1;
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.getRank(currentID + 10));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.getRank(-1));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.setRoleID(currentID + 10, "0"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.setRoleID(-1, "0"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.setName(currentID + 10, "Fail"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.setName(-1, "Fail"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.setDescription(currentID + 10, "Fail"));
        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.RANKS.setDescription(-1, "Fail"));

    }

}
