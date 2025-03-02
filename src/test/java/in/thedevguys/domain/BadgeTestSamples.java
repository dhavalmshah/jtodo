package in.thedevguys.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BadgeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Badge getBadgeSample1() {
        return new Badge().id(1L).name("name1").description("description1").image("image1").criteria("criteria1");
    }

    public static Badge getBadgeSample2() {
        return new Badge().id(2L).name("name2").description("description2").image("image2").criteria("criteria2");
    }

    public static Badge getBadgeRandomSampleGenerator() {
        return new Badge()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .image(UUID.randomUUID().toString())
            .criteria(UUID.randomUUID().toString());
    }
}
