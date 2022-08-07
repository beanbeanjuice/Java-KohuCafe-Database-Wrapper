import com.beanbeanjuice.KohuCafeDatabaseConnection;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWarnHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");

    @Test
    @DisplayName("Test User Handler")
    public void testUserHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeDatabaseConnection.WARNS.getActiveUserWarns("690927484199370753"));
        Assertions.assertEquals(1, kohuCafeDatabaseConnection.WARNS.getActiveUserWarns("690927484199370753").get(0).getWarnID());
        Assertions.assertTrue(() -> kohuCafeDatabaseConnection.WARNS.getAllUserWarns("690927484199370753").size() >= 2);
        Assertions.assertEquals("beanbeanjuice", kohuCafeDatabaseConnection.WARNS.getActiveUserWarns("690927484199370753").get(0).getIssuerNickname());

        int prevID = kohuCafeDatabaseConnection.WARNS.getAllWarns().size();
        int numberOfWarns = kohuCafeDatabaseConnection.WARNS.getAllUserWarns("690927484199370753").size();
        int numberOfActiveWarns = kohuCafeDatabaseConnection.WARNS.getActiveUserWarns("690927484199370753").size();
        Assertions.assertTrue(kohuCafeDatabaseConnection.WARNS.addWarn("690927484199370753", "beanbeanjuice",
                "690927484199370753", "beanbeanjuice", "Testing!"));
        Assertions.assertEquals(prevID+1, kohuCafeDatabaseConnection.WARNS.getAllUserWarns("690927484199370753").get(numberOfWarns).getWarnID());
        Assertions.assertEquals(numberOfActiveWarns+1, kohuCafeDatabaseConnection.WARNS.getActiveUserWarns("690927484199370753").size());
        Assertions.assertEquals(numberOfWarns+1, kohuCafeDatabaseConnection.WARNS.getAllUserWarns("690927484199370753").size());
        Assertions.assertTrue(kohuCafeDatabaseConnection.WARNS.updateWarnActiveStatus(prevID+1, false));
    }

}
