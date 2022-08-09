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
    private static final String OWNER = "690927484199370753";

    @Test
    @DisplayName("Test Avatar Inventory Handler")
    public void testAvatarInventoryHandler() {
        KohuCafeDatabaseConnection kohuCafeDatabaseConnection = new KohuCafeDatabaseConnection(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeDatabaseConnection.TYPE.BETA);

        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATARS.hasItem(OWNER, 1));
        Assertions.assertFalse(kohuCafeDatabaseConnection.AVATARS.hasItem(OWNER, 2));

        Assertions.assertThrows(AvatarItemDoesNotExistException.class, () -> kohuCafeDatabaseConnection.AVATARS.giveItem(OWNER, 0));
        Assertions.assertTrue(() -> kohuCafeDatabaseConnection.AVATARS.getAvatarItems(OWNER).size() >= 1);

        int id = 2;
        String name = "Test Item #2";
        String description = "Description for Test Item #2";
        String imageURL = "https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361514_960_720.png";
        int total = 1;
        double weight = 0;
        double damage = 0;

        // Test if item updates in inventory.
        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATARS.giveItem(OWNER, 2));
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

        Assertions.assertNotNull(kohuCafeDatabaseConnection.AVATARS.getAvatarItem(OWNER, 2));
        Assertions.assertEquals(1.0, kohuCafeDatabaseConnection.AVATARS.getAvatarItem(OWNER, 2).getWeight());
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
        Assertions.assertEquals(0.0, kohuCafeDatabaseConnection.AVATARS.getAvatarItem(OWNER, 2).getWeight());

        Assertions.assertTrue(kohuCafeDatabaseConnection.AVATARS.removeItem(OWNER, 2));
        Assertions.assertNull(kohuCafeDatabaseConnection.AVATARS.getAvatarItem(OWNER, 2));
        Assertions.assertFalse(kohuCafeDatabaseConnection.AVATARS.giveItem(OWNER, 3));
    }

}
