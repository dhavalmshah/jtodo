package in.thedevguys.domain;

import static in.thedevguys.domain.NotificationTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.UserAttributesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = getNotificationSample1();
        Notification notification2 = new Notification();
        assertThat(notification1).isNotEqualTo(notification2);

        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);

        notification2 = getNotificationSample2();
        assertThat(notification1).isNotEqualTo(notification2);
    }

    @Test
    void userTest() {
        Notification notification = getNotificationRandomSampleGenerator();
        UserAttributes userAttributesBack = getUserAttributesRandomSampleGenerator();

        notification.setUser(userAttributesBack);
        assertThat(notification.getUser()).isEqualTo(userAttributesBack);

        notification.user(null);
        assertThat(notification.getUser()).isNull();
    }

    @Test
    void taskTest() {
        Notification notification = getNotificationRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        notification.setTask(todoBack);
        assertThat(notification.getTask()).isEqualTo(todoBack);

        notification.task(null);
        assertThat(notification.getTask()).isNull();
    }
}
