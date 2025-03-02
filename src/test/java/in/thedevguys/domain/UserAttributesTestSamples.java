package in.thedevguys.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UserAttributesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UserAttributes getUserAttributesSample1() {
        return new UserAttributes().id(1L).name("name1").email("email1").password("password1").level(1L).points(1L);
    }

    public static UserAttributes getUserAttributesSample2() {
        return new UserAttributes().id(2L).name("name2").email("email2").password("password2").level(2L).points(2L);
    }

    public static UserAttributes getUserAttributesRandomSampleGenerator() {
        return new UserAttributes()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .level(longCount.incrementAndGet())
            .points(longCount.incrementAndGet());
    }
}
