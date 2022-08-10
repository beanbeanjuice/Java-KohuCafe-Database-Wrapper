import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.utility.exception.rank.RankDoesNotExistException;
import com.beanbeanjuice.utility.exception.user.UserAlreadyHasRankException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotExistException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotHaveRankException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUserRankHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");
    private static final String OWNER = "690927484199370753";

    @Test
    @DisplayName("Test User Rank Handler")
    public void testUserRankHandler() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeAPI.USER_RANKS.getUserRanks(OWNER));
        Assertions.assertEquals("Programmer", kohuCafeAPI.USER_RANKS.getUserRanks(OWNER).get(0).getName());
        Assertions.assertEquals("A rank given to programmers on the server.", kohuCafeAPI.USER_RANKS.getUserRanks(OWNER).get(0).getDescription());
        Assertions.assertEquals("1005742939311452200", kohuCafeAPI.USER_RANKS.getUserRanks(OWNER).get(0).getRoleID());
    }

    @Test
    @DisplayName("Test User Rank Handler Exceptions")
    public void testUserRankHandlerExceptions() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertDoesNotThrow(() -> kohuCafeAPI.USER_RANKS.getUserRankIDs("0"));
        Assertions.assertDoesNotThrow(() -> kohuCafeAPI.USER_RANKS.getUserRanks("0"));

        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.USER_RANKS.addRankToUser("0", 0));
        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeAPI.USER_RANKS.addRankToUser("0", 1));
        Assertions.assertThrows(UserAlreadyHasRankException.class, () -> kohuCafeAPI.USER_RANKS.addRankToUser(OWNER, 1));

        Assertions.assertThrows(RankDoesNotExistException.class, () -> kohuCafeAPI.USER_RANKS.removeRankFromUser("0", 0));
        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeAPI.USER_RANKS.removeRankFromUser("0", 1));
        Assertions.assertThrows(UserDoesNotHaveRankException.class, () -> kohuCafeAPI.USER_RANKS.removeRankFromUser(OWNER, 2));
    }

}
