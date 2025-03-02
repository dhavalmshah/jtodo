package in.thedevguys.service.mapper;

import static in.thedevguys.domain.UserAttributesAsserts.*;
import static in.thedevguys.domain.UserAttributesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAttributesMapperTest {

    private UserAttributesMapper userAttributesMapper;

    @BeforeEach
    void setUp() {
        userAttributesMapper = new UserAttributesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUserAttributesSample1();
        var actual = userAttributesMapper.toEntity(userAttributesMapper.toDto(expected));
        assertUserAttributesAllPropertiesEquals(expected, actual);
    }
}
