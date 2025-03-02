package in.thedevguys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAttributesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAttributesDTO.class);
        UserAttributesDTO userAttributesDTO1 = new UserAttributesDTO();
        userAttributesDTO1.setId(1L);
        UserAttributesDTO userAttributesDTO2 = new UserAttributesDTO();
        assertThat(userAttributesDTO1).isNotEqualTo(userAttributesDTO2);
        userAttributesDTO2.setId(userAttributesDTO1.getId());
        assertThat(userAttributesDTO1).isEqualTo(userAttributesDTO2);
        userAttributesDTO2.setId(2L);
        assertThat(userAttributesDTO1).isNotEqualTo(userAttributesDTO2);
        userAttributesDTO1.setId(null);
        assertThat(userAttributesDTO1).isNotEqualTo(userAttributesDTO2);
    }
}
