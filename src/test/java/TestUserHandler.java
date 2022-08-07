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
    }

}
