import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.utility.exception.WarnDoesNotExistException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWarnHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");
    private static final String OWNER = "690927484199370753";

    @Test
    @DisplayName("Test User Handler")
    public void testWarnHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeDatabaseConnection.WARNS.getActiveUserWarns(OWNER));
        Assertions.assertEquals(1, kohuCafeDatabaseConnection.WARNS.getActiveUserWarns(OWNER).get(0).getWarnID());
        Assertions.assertTrue(() -> kohuCafeDatabaseConnection.WARNS.getAllUserWarns(OWNER).size() >= 2);
        Assertions.assertEquals("beanbeanjuice", kohuCafeDatabaseConnection.WARNS.getActiveUserWarns(OWNER).get(0).getIssuerNickname());

        int prevID = kohuCafeDatabaseConnection.WARNS.getAllWarns().size();
        int numberOfWarns = kohuCafeDatabaseConnection.WARNS.getAllUserWarns(OWNER).size();
        int numberOfActiveWarns = kohuCafeDatabaseConnection.WARNS.getActiveUserWarns(OWNER).size();
        Assertions.assertTrue(kohuCafeDatabaseConnection.WARNS.addWarn(OWNER, "beanbeanjuice",
                OWNER, "beanbeanjuice", "Testing!"));
        Assertions.assertEquals(prevID+1, kohuCafeDatabaseConnection.WARNS.getAllUserWarns(OWNER).get(numberOfWarns).getWarnID());
        Assertions.assertEquals(numberOfActiveWarns+1, kohuCafeDatabaseConnection.WARNS.getActiveUserWarns(OWNER).size());
        Assertions.assertEquals(numberOfWarns+1, kohuCafeDatabaseConnection.WARNS.getAllUserWarns(OWNER).size());
        Assertions.assertTrue(kohuCafeDatabaseConnection.WARNS.updateWarnActiveStatus(prevID+1, false));
    }

    @Test
    @DisplayName("Test Warn Handler Exceptions")
    public void testWarnHandlerExceptions() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);

        Assertions.assertThrows(WarnDoesNotExistException.class, () -> kohuCafeDatabaseConnection.WARNS.getWarn(0));
        Assertions.assertThrows(WarnDoesNotExistException.class, () -> kohuCafeDatabaseConnection.WARNS.updateWarnActiveStatus(0, false));
    }

}
