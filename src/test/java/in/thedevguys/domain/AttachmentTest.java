package in.thedevguys.domain;

import static in.thedevguys.domain.AttachmentTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.UserAttributesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = getAttachmentSample1();
        Attachment attachment2 = new Attachment();
        assertThat(attachment1).isNotEqualTo(attachment2);

        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);

        attachment2 = getAttachmentSample2();
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    void uploaderTest() {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        UserAttributes userAttributesBack = getUserAttributesRandomSampleGenerator();

        attachment.setUploader(userAttributesBack);
        assertThat(attachment.getUploader()).isEqualTo(userAttributesBack);

        attachment.uploader(null);
        assertThat(attachment.getUploader()).isNull();
    }

    @Test
    void todoTest() {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        attachment.setTodo(todoBack);
        assertThat(attachment.getTodo()).isEqualTo(todoBack);

        attachment.todo(null);
        assertThat(attachment.getTodo()).isNull();
    }
}
