import com.beanbeanjuice.KohuCafeDatabaseConnection;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUserHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");

    @Test
    @DisplayName("Test User Handler")
    public void testUserHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeDatabaseConnection.USERS.getUser("690927484199370753"));
        Assertions.assertEquals(1.5, kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getMultiplier());
        Assertions.assertEquals(5000.0, kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getBalance());
        Assertions.assertEquals("Programmer", kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getRanks().get(0).getName());

        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateBalance("690927484199370753", 123.5));
        Assertions.assertEquals(123.5, kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getBalance());
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateBalance("690927484199370753", 5000.0));
        Assertions.assertEquals(5000.0, kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getBalance());

        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateMultiplier("690927484199370753", 2.0));
        Assertions.assertEquals(2.0, kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getMultiplier());
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateMultiplier("690927484199370753", 1.5));
        Assertions.assertEquals(1.5, kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").getMultiplier());

        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.addRankToUser("690927484199370753", 2));
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").hasRank(2));
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.removeRankFromUser("690927484199370753", 2));
        Assertions.assertFalse(kohuCafeDatabaseConnection.USERS.getUser("690927484199370753").hasRank(2));
    }

}
