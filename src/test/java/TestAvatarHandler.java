import com.beanbeanjuice.KohuCafeAPI;
import com.beanbeanjuice.tables.avatar.Avatar;
import com.beanbeanjuice.utility.exception.avatar.AvatarAlreadyExistsException;
import com.beanbeanjuice.utility.exception.avatar.AvatarDoesNotExistException;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAvatarHandler {

    private static final Dotenv DOT_ENV = Dotenv.load();
    private static final String MYSQL_USERNAME = DOT_ENV.get("MYSQL_USERNAME");
    private static final String MYSQL_PASSWORD = DOT_ENV.get("MYSQL_PASSWORD");
    private static final String OWNER = "690927484199370753";
    private static final String FRIEND = "281325818754498561";
    private static final String FRIEND_2 = "421405302446096384";

    @Test
    @DisplayName("Test Avatar Handler")
    public void testAvatarHandler() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertThrows(AvatarAlreadyExistsException.class, () -> kohuCafeAPI.AVATARS.addAvatar(OWNER));
        Assertions.assertEquals(100, kohuCafeAPI.AVATARS.getAvatar(FRIEND).getMaxHealth());

        Avatar avatar = kohuCafeAPI.AVATARS.getAvatar(FRIEND);
        Integer maxHealth = 500;
        Assertions.assertTrue(() -> kohuCafeAPI.AVATARS.updateAvatar(
                FRIEND,
                maxHealth,
                avatar.getStrength(),
                avatar.getIntelligence()
        ));
        Assertions.assertEquals(500, kohuCafeAPI.AVATARS.getAvatar(FRIEND).getMaxHealth());
        Assertions.assertTrue(() -> kohuCafeAPI.AVATARS.updateAvatar(
                FRIEND,
                100,
                avatar.getStrength(),
                avatar.getIntelligence()
        ));
        Assertions.assertEquals(100, kohuCafeAPI.AVATARS.getAvatar(FRIEND).getMaxHealth());

        Assertions.assertEquals(0, kohuCafeAPI.AVATARS.getAvatar(OWNER).getLevel());
        Assertions.assertEquals(99, kohuCafeAPI.AVATARS.getAvatar(OWNER).getExperience());
        Assertions.assertEquals(1, kohuCafeAPI.AVATARS.getAvatar(OWNER).getExperienceToNextLevel());

        Assertions.assertEquals(1, kohuCafeAPI.AVATARS.getAvatar(FRIEND).getLevel());
        Assertions.assertEquals(998, kohuCafeAPI.AVATARS.getAvatar(FRIEND).getExperience());
        Assertions.assertEquals(2, kohuCafeAPI.AVATARS.getAvatar(FRIEND).getExperienceToNextLevel());

        int currentExperience = kohuCafeAPI.AVATARS.getAvatar(FRIEND_2).getExperience();
        int currentLevel = kohuCafeAPI.AVATARS.getAvatar(FRIEND_2).getLevel();
        int experienceToNextLevel = kohuCafeAPI.AVATARS.getAvatar(FRIEND_2).getExperienceToNextLevel();

        Assertions.assertTrue(kohuCafeAPI.AVATARS.addExperience(FRIEND_2, experienceToNextLevel + 1));
        Assertions.assertEquals(currentExperience + experienceToNextLevel + 1, kohuCafeAPI.AVATARS.getAvatar(FRIEND_2).getExperience());
        Assertions.assertEquals(currentLevel + 1, kohuCafeAPI.AVATARS.getAvatar(FRIEND_2).getLevel());
        Assertions.assertTrue(kohuCafeAPI.AVATARS.addExperience(FRIEND_2, (experienceToNextLevel + 1) * -1));
        Assertions.assertEquals(currentExperience, kohuCafeAPI.AVATARS.getAvatar(FRIEND_2).getExperience());
    }

    @Test
    @DisplayName("Test Avatar Handler Exceptions")
    public void testAvatarHandlerExceptions() {
        KohuCafeAPI kohuCafeAPI = new KohuCafeAPI(MYSQL_USERNAME, MYSQL_PASSWORD, KohuCafeAPI.TYPE.BETA);

        Assertions.assertThrows(AvatarAlreadyExistsException.class, () -> kohuCafeAPI.AVATARS.addAvatar(OWNER));
        Assertions.assertThrows(AvatarDoesNotExistException.class, () -> kohuCafeAPI.AVATARS.updateAvatar("0", 10000, 1000, 1000));
        Assertions.assertThrows(AvatarDoesNotExistException.class, () -> kohuCafeAPI.AVATARS.addExperience("0", 500));

    }

}
