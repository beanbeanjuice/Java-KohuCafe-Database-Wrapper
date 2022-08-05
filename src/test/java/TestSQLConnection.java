import com.beanbeanjuice.utility.sql.SQLConnection;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSQLConnection {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_URL = DOT_ENV.get("MYSQL_URL");
    private static final Integer MYSQL_PORT = Integer.parseInt(DOT_ENV.get("MYSQL_PORT"));
    private static final String MYSQL_SCHEMA = DOT_ENV.get("MYSQL_SCHEMA");
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");

    @Test
    @DisplayName("Test Connection")
    public void testConnection() {
        SQLConnection connection = new SQLConnection(MYSQL_URL, MYSQL_PORT, MYSQL_SCHEMA, MYSQL_USERNAME, MYSQL_PASSWORD);

        Assertions.assertTrue(connection.start());
    }

}
