import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.tables.avatar.AvatarItem;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAvatarItemsHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");

    @Test
    @DisplayName("Test Avatar Items Handler")
    public void testAvatarItemsHandler() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);
        int newItemID = kohuCafeAPI.AVATAR_ITEMS.getItems().size()+1;

        Assertions.assertEquals("The Keyboard", kohuCafeAPI.AVATAR_ITEMS.getItem(1).getName());
        String name = "Test Item #" + newItemID;
        String description = "Description for " + name;
        String imageURL = "https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361514_960_720.png";
        int total = 1;
        double weight = 0;
        double damage = 0;

        Assertions.assertTrue(kohuCafeAPI.AVATAR_ITEMS.addNewItem(
                name, description, imageURL, total, weight, damage
        ));
        Assertions.assertEquals(name, kohuCafeAPI.AVATAR_ITEMS.getItem(newItemID).getName());
        Assertions.assertEquals(newItemID, kohuCafeAPI.AVATAR_ITEMS.getItems().size());
        Assertions.assertTrue(kohuCafeAPI.AVATAR_ITEMS.updateItem(
                new AvatarItem(newItemID, name, description, imageURL, total, weight + 5, damage)
        ));
        Assertions.assertEquals(5.0, kohuCafeAPI.AVATAR_ITEMS.getItem(newItemID).getWeight());
    }

}
