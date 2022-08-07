import com.beanbeanjuice.KohuCafeDatabaseConnection;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUserRankHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");

    @Test
    @DisplayName("Test User Rank Handler")
    public void testUserRankHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeDatabaseConnection.USER_RANKS.getUserRanks("690927484199370753"));
        Assertions.assertEquals("Programmer", kohuCafeDatabaseConnection.USER_RANKS.getUserRanks("690927484199370753").get(0).getName());
        Assertions.assertEquals("A rank given to programmers on the server.", kohuCafeDatabaseConnection.USER_RANKS.getUserRanks("690927484199370753").get(0).getDescription());
        Assertions.assertEquals("1005742939311452200", kohuCafeDatabaseConnection.USER_RANKS.getUserRanks("690927484199370753").get(0).getRoleID());
    }

}
