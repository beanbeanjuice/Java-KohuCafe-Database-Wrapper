import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.tables.avatar.AvatarItem;
import com.beanbeanjuice.utility.exception.item.AvatarInventoryDoesNotContainItemException;
import com.beanbeanjuice.utility.exception.item.AvatarInventoryDoesNotExistException;
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
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertTrue(kohuCafeAPI.AVATAR_INVENTORY.hasItem(OWNER, 1));
        Assertions.assertFalse(kohuCafeAPI.AVATAR_INVENTORY.hasItem(OWNER, 2));

        Assertions.assertThrows(AvatarItemDoesNotExistException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.giveItem(OWNER, 0));
        Assertions.assertTrue(() -> kohuCafeAPI.AVATAR_INVENTORY.getAvatarItems(OWNER).size() >= 1);

        int id = 2;
        String name = "Test Item #2";
        String description = "Description for Test Item #2";
        String imageURL = "https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361514_960_720.png";
        int total = 1;
        double weight = 0;
        double damage = 0;

        // Test if item updates in inventory.
        Assertions.assertTrue(kohuCafeAPI.AVATAR_INVENTORY.giveItem(OWNER, 2));
        Assertions.assertTrue(kohuCafeAPI.AVATAR_ITEMS.updateItem(
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

        Assertions.assertNotNull(kohuCafeAPI.AVATAR_INVENTORY.getAvatarItem(OWNER, 2));
        Assertions.assertEquals(1.0, kohuCafeAPI.AVATAR_INVENTORY.getAvatarItem(OWNER, 2).getWeight());
        Assertions.assertTrue(kohuCafeAPI.AVATAR_ITEMS.updateItem(
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
        Assertions.assertEquals(0.0, kohuCafeAPI.AVATAR_INVENTORY.getAvatarItem(OWNER, 2).getWeight());

        Assertions.assertTrue(kohuCafeAPI.AVATAR_INVENTORY.removeItem(OWNER, 2));
        Assertions.assertThrows(AvatarInventoryDoesNotContainItemException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.getAvatarItem(OWNER, 2));
        Assertions.assertFalse(kohuCafeAPI.AVATAR_INVENTORY.giveItem(OWNER, 3));
    }

    @Test
    @DisplayName("Test Avatar Inventory Handler Exceptions")
    public void testAvatarInventoryHandlerExceptions() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertThrows(AvatarItemDoesNotExistException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.giveItem(OWNER, 0));
        Assertions.assertThrows(AvatarInventoryDoesNotExistException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.getAvatarItem("0", 1));
        Assertions.assertThrows(AvatarInventoryDoesNotContainItemException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.getAvatarItem(OWNER, 4));

        Assertions.assertThrows(AvatarItemDoesNotExistException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.giveItem(OWNER, 0));
        Assertions.assertThrows(AvatarItemDoesNotExistException.class, () -> kohuCafeAPI.AVATAR_INVENTORY.removeItem(OWNER, 0));
    }

}
