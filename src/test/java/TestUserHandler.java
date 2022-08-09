import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.utility.exception.user.UserAlreadyExistsException;
import com.beanbeanjuice.utility.exception.user.UserDoesNotExistException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUserHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");
    private static final String OWNER = "690927484199370753";

    @Test
    @DisplayName("Test User Handler")
    public void testUserHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeDatabaseConnection.USERS.getUser(OWNER));
        Assertions.assertEquals(1.5, kohuCafeDatabaseConnection.USERS.getUser(OWNER).getMultiplier());
        Assertions.assertEquals(5000.0, kohuCafeDatabaseConnection.USERS.getUser(OWNER).getBalance());
        Assertions.assertEquals("Programmer", kohuCafeDatabaseConnection.USERS.getUser(OWNER).getRanks().get(0).getName());

        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateBalance(OWNER, 123.5));
        Assertions.assertEquals(123.5, kohuCafeDatabaseConnection.USERS.getUser(OWNER).getBalance());
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateBalance(OWNER, 5000.0));
        Assertions.assertEquals(5000.0, kohuCafeDatabaseConnection.USERS.getUser(OWNER).getBalance());

        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateMultiplier(OWNER, 2.0));
        Assertions.assertEquals(2.0, kohuCafeDatabaseConnection.USERS.getUser(OWNER).getMultiplier());
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.updateMultiplier(OWNER, 1.5));
        Assertions.assertEquals(1.5, kohuCafeDatabaseConnection.USERS.getUser(OWNER).getMultiplier());

        Assertions.assertTrue(kohuCafeDatabaseConnection.USER_RANKS.addRankToUser(OWNER, 2));
        Assertions.assertTrue(kohuCafeDatabaseConnection.USERS.getUser(OWNER).hasRank(2));
        Assertions.assertTrue(kohuCafeDatabaseConnection.USER_RANKS.removeRankFromUser(OWNER, 2));
        Assertions.assertFalse(kohuCafeDatabaseConnection.USERS.getUser(OWNER).hasRank(2));
    }

    @Test
    @DisplayName("Test User Handler Exceptions")
    public void testUserHandlerExceptions() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);

        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeDatabaseConnection.USERS.getUser("0"));
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> kohuCafeDatabaseConnection.USERS.addUser(OWNER));
        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeDatabaseConnection.USERS.updateBalance("0", 100000.0));
        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeDatabaseConnection.USERS.updateMultiplier("0", 10000.0));
    }

}
