package in.thedevguys.domain;

import static in.thedevguys.domain.BadgeTestSamples.*;
import static in.thedevguys.domain.UserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BadgeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Badge.class);
        Badge badge1 = getBadgeSample1();
        Badge badge2 = new Badge();
        assertThat(badge1).isNotEqualTo(badge2);

        badge2.setId(badge1.getId());
        assertThat(badge1).isEqualTo(badge2);

        badge2 = getBadgeSample2();
        assertThat(badge1).isNotEqualTo(badge2);
    }

    @Test
    void usersTest() {
        Badge badge = getBadgeRandomSampleGenerator();
        User userAttributesBack = getUserAttributesRandomSampleGenerator();

        badge.addUsers(userAttributesBack);
        assertThat(badge.getUsers()).containsOnly(userAttributesBack);
        assertThat(userAttributesBack.getBadges()).containsOnly(badge);

        badge.removeUsers(userAttributesBack);
        assertThat(badge.getUsers()).doesNotContain(userAttributesBack);
        assertThat(userAttributesBack.getBadges()).doesNotContain(badge);

        badge.users(new HashSet<>(Set.of(userAttributesBack)));
        assertThat(badge.getUsers()).containsOnly(userAttributesBack);
        assertThat(userAttributesBack.getBadges()).containsOnly(badge);

        badge.setUsers(new HashSet<>());
        assertThat(badge.getUsers()).doesNotContain(userAttributesBack);
        assertThat(userAttributesBack.getBadges()).doesNotContain(badge);
    }
}
