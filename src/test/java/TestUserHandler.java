import com.beanbeanjuice.KohuCafeAPI;
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
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeAPI.USERS.getUser(OWNER));
        Assertions.assertEquals(1.5, kohuCafeAPI.USERS.getUser(OWNER).getMultiplier());
        Assertions.assertEquals(5000.0, kohuCafeAPI.USERS.getUser(OWNER).getBalance());
        Assertions.assertEquals("Programmer", kohuCafeAPI.USERS.getUser(OWNER).getRanks().get(0).getName());

        Assertions.assertTrue(kohuCafeAPI.USERS.updateBalance(OWNER, 123.5));
        Assertions.assertEquals(123.5, kohuCafeAPI.USERS.getUser(OWNER).getBalance());
        Assertions.assertTrue(kohuCafeAPI.USERS.updateBalance(OWNER, 5000.0));
        Assertions.assertEquals(5000.0, kohuCafeAPI.USERS.getUser(OWNER).getBalance());

        Assertions.assertTrue(kohuCafeAPI.USERS.updateMultiplier(OWNER, 2.0));
        Assertions.assertEquals(2.0, kohuCafeAPI.USERS.getUser(OWNER).getMultiplier());
        Assertions.assertTrue(kohuCafeAPI.USERS.updateMultiplier(OWNER, 1.5));
        Assertions.assertEquals(1.5, kohuCafeAPI.USERS.getUser(OWNER).getMultiplier());

        Assertions.assertTrue(kohuCafeAPI.USER_RANKS.addRankToUser(OWNER, 2));
        Assertions.assertTrue(kohuCafeAPI.USERS.getUser(OWNER).hasRank(2));
        Assertions.assertTrue(kohuCafeAPI.USER_RANKS.removeRankFromUser(OWNER, 2));
        Assertions.assertFalse(kohuCafeAPI.USERS.getUser(OWNER).hasRank(2));
    }

    @Test
    @DisplayName("Test User Handler Exceptions")
    public void testUserHandlerExceptions() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeAPI.USERS.getUser("0"));
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> kohuCafeAPI.USERS.addUser(OWNER));
        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeAPI.USERS.updateBalance("0", 100000.0));
        Assertions.assertThrows(UserDoesNotExistException.class, () -> kohuCafeAPI.USERS.updateMultiplier("0", 10000.0));
    }

}
