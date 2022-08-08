import com.beanbeanjuice.KohuCafeDatabaseConnection;
import com.beanbeanjuice.tables.avatar.AvatarItem;
import com.beanbeanjuice.utility.exception.item.AvatarItemDoesNotExistException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAvatarInventoryHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");

    @Test
    @DisplayName("Test Avatar Inventory Handler")
    public void testAvatarInventoryHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);

        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATARS.hasItem("690927484199370753", 1));
        Assertions.assertFalse(kohuCafeDatabaseConnection.AVATARS.hasItem("690927484199370753", 2));

        Assertions.assertThrows(AvatarItemDoesNotExistException.class, () -> kohuCafeDatabaseConnection.AVATARS.giveItem("690927484199370753", 0));
        Assertions.assertTrue(() -> kohuCafeDatabaseConnection.AVATARS.getAvatarItems("690927484199370753").size() >= 1);

        int id = 2;
        String name = "";
        String description = "";
        String imageURL = "";
        int total = 1;
        double weight = 0;
        double damage = 0;

        // Test if item updates in inventory.
        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATARS.giveItem("690927484199370753", 2));
        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATAR_ITEMS.updateItem(
                new AvatarItem(
                        id,
                        name,
                        description,
                        imageURL,
                        total,
                        weight+1,
                        damage
                )
        ));

        Assertions.assertNotNull(kohuCafeDatabaseConnection.AVATARS.getAvatarItem("690927484199370753", 2));
        Assertions.assertEquals(1.0, kohuCafeDatabaseConnection.AVATARS.getAvatarItem("690927484199370753", 2).getWeight());
        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATAR_ITEMS.updateItem(
                new AvatarItem(
                        id,
                        name,
                        description,
                        imageURL,
                        total,
                        weight,
                        damage
                )
        ));
        Assertions.assertEquals(0.0, kohuCafeDatabaseConnection.AVATARS.getAvatarItem("690927484199370753", 2).getWeight());

        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATARS.removeItem("690927484199370753", 2));
        Assertions.assertNull(kohuCafeDatabaseConnection.AVATARS.getAvatarItem("690927484199370753", 2));

    }

}
