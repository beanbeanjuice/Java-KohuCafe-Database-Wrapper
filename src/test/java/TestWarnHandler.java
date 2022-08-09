import com.beanbeanjuice.KohuCafeAPI;
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
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);
        Assertions.assertNotNull(kohuCafeAPI.WARNS.getActiveUserWarns(OWNER));
        Assertions.assertEquals(1, kohuCafeAPI.WARNS.getActiveUserWarns(OWNER).get(0).getWarnID());
        Assertions.assertTrue(() -> kohuCafeAPI.WARNS.getAllUserWarns(OWNER).size() >= 2);
        Assertions.assertEquals("beanbeanjuice", kohuCafeAPI.WARNS.getActiveUserWarns(OWNER).get(0).getIssuerNickname());

        int prevID = kohuCafeAPI.WARNS.getAllWarns().size();
        int numberOfWarns = kohuCafeAPI.WARNS.getAllUserWarns(OWNER).size();
        int numberOfActiveWarns = kohuCafeAPI.WARNS.getActiveUserWarns(OWNER).size();
        Assertions.assertTrue(kohuCafeAPI.WARNS.addWarn(OWNER, "beanbeanjuice",
                OWNER, "beanbeanjuice", "Testing!"));
        Assertions.assertEquals(prevID+1, kohuCafeAPI.WARNS.getAllUserWarns(OWNER).get(numberOfWarns).getWarnID());
        Assertions.assertEquals(numberOfActiveWarns+1, kohuCafeAPI.WARNS.getActiveUserWarns(OWNER).size());
        Assertions.assertEquals(numberOfWarns+1, kohuCafeAPI.WARNS.getAllUserWarns(OWNER).size());
        Assertions.assertTrue(kohuCafeAPI.WARNS.updateWarnActiveStatus(prevID+1, false));
    }

    @Test
    @DisplayName("Test Warn Handler Exceptions")
    public void testWarnHandlerExceptions() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertThrows(WarnDoesNotExistException.class, () -> kohuCafeAPI.WARNS.getWarn(0));
        Assertions.assertThrows(WarnDoesNotExistException.class, () -> kohuCafeAPI.WARNS.updateWarnActiveStatus(0, false));
    }

}
