package in.thedevguys.service.mapper;

import static in.thedevguys.domain.BadgeAsserts.*;
import static in.thedevguys.domain.BadgeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BadgeMapperTest {

    private BadgeMapper badgeMapper;

    @BeforeEach
    void setUp() {
        badgeMapper = new BadgeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBadgeSample1();
        var actual = badgeMapper.toEntity(badgeMapper.toDto(expected));
        assertBadgeAllPropertiesEquals(expected, actual);
    }
}
