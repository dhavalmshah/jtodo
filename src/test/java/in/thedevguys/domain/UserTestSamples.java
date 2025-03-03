package in.thedevguys.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static User getUserAttributesSample1() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("fname1");
        user.setLastName("lname1");
        user.setEmail("email1");
        user.setPassword("password1");
        user.setLevel(1L);
        user.setPoints(1L);
        return user;
    }

    public static User getUserAttributesSample2() {
        User user = new User();
        user.setId(2L);
        user.setFirstName("fname2");
        user.setLastName("lname2");
        user.setEmail("email2");
        user.setPassword("password2");
        user.setLevel(2L);
        user.setPoints(2L);
        return user;
    }

    public static User getUserAttributesRandomSampleGenerator() {
        User user = new User();
        user.setId(longCount.incrementAndGet());
        user.setFirstName(UUID.randomUUID().toString());
        user.setLastName(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPassword(UUID.randomUUID().toString());
        user.setLevel(longCount.incrementAndGet());
        user.setPoints(longCount.incrementAndGet());
        return user;
    }
}
