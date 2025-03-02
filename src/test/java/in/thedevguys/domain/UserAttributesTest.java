package in.thedevguys.domain;

import static in.thedevguys.domain.AttachmentTestSamples.*;
import static in.thedevguys.domain.BadgeTestSamples.*;
import static in.thedevguys.domain.CommentTestSamples.*;
import static in.thedevguys.domain.NotificationTestSamples.*;
import static in.thedevguys.domain.ProjectTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.UserAttributesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAttributes.class);
        UserAttributes userAttributes1 = getUserAttributesSample1();
        UserAttributes userAttributes2 = new UserAttributes();
        assertThat(userAttributes1).isNotEqualTo(userAttributes2);

        userAttributes2.setId(userAttributes1.getId());
        assertThat(userAttributes1).isEqualTo(userAttributes2);

        userAttributes2 = getUserAttributesSample2();
        assertThat(userAttributes1).isNotEqualTo(userAttributes2);
    }

    @Test
    void commentsTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        userAttributes.addComments(commentBack);
        assertThat(userAttributes.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getUser()).isEqualTo(userAttributes);

        userAttributes.removeComments(commentBack);
        assertThat(userAttributes.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getUser()).isNull();

        userAttributes.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(userAttributes.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getUser()).isEqualTo(userAttributes);

        userAttributes.setComments(new HashSet<>());
        assertThat(userAttributes.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getUser()).isNull();
    }

    @Test
    void projectsOwnedTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        userAttributes.addProjectsOwned(projectBack);
        assertThat(userAttributes.getProjectsOwneds()).containsOnly(projectBack);
        assertThat(projectBack.getOwner()).isEqualTo(userAttributes);

        userAttributes.removeProjectsOwned(projectBack);
        assertThat(userAttributes.getProjectsOwneds()).doesNotContain(projectBack);
        assertThat(projectBack.getOwner()).isNull();

        userAttributes.projectsOwneds(new HashSet<>(Set.of(projectBack)));
        assertThat(userAttributes.getProjectsOwneds()).containsOnly(projectBack);
        assertThat(projectBack.getOwner()).isEqualTo(userAttributes);

        userAttributes.setProjectsOwneds(new HashSet<>());
        assertThat(userAttributes.getProjectsOwneds()).doesNotContain(projectBack);
        assertThat(projectBack.getOwner()).isNull();
    }

    @Test
    void todosCreatedTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        userAttributes.addTodosCreated(todoBack);
        assertThat(userAttributes.getTodosCreateds()).containsOnly(todoBack);
        assertThat(todoBack.getCreator()).isEqualTo(userAttributes);

        userAttributes.removeTodosCreated(todoBack);
        assertThat(userAttributes.getTodosCreateds()).doesNotContain(todoBack);
        assertThat(todoBack.getCreator()).isNull();

        userAttributes.todosCreateds(new HashSet<>(Set.of(todoBack)));
        assertThat(userAttributes.getTodosCreateds()).containsOnly(todoBack);
        assertThat(todoBack.getCreator()).isEqualTo(userAttributes);

        userAttributes.setTodosCreateds(new HashSet<>());
        assertThat(userAttributes.getTodosCreateds()).doesNotContain(todoBack);
        assertThat(todoBack.getCreator()).isNull();
    }

    @Test
    void notificationsTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        userAttributes.addNotifications(notificationBack);
        assertThat(userAttributes.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getUser()).isEqualTo(userAttributes);

        userAttributes.removeNotifications(notificationBack);
        assertThat(userAttributes.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getUser()).isNull();

        userAttributes.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(userAttributes.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getUser()).isEqualTo(userAttributes);

        userAttributes.setNotifications(new HashSet<>());
        assertThat(userAttributes.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getUser()).isNull();
    }

    @Test
    void attachmentsTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        userAttributes.addAttachments(attachmentBack);
        assertThat(userAttributes.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getUploader()).isEqualTo(userAttributes);

        userAttributes.removeAttachments(attachmentBack);
        assertThat(userAttributes.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getUploader()).isNull();

        userAttributes.attachments(new HashSet<>(Set.of(attachmentBack)));
        assertThat(userAttributes.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getUploader()).isEqualTo(userAttributes);

        userAttributes.setAttachments(new HashSet<>());
        assertThat(userAttributes.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getUploader()).isNull();
    }

    @Test
    void assignedTodosTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        userAttributes.addAssignedTodos(todoBack);
        assertThat(userAttributes.getAssignedTodos()).containsOnly(todoBack);

        userAttributes.removeAssignedTodos(todoBack);
        assertThat(userAttributes.getAssignedTodos()).doesNotContain(todoBack);

        userAttributes.assignedTodos(new HashSet<>(Set.of(todoBack)));
        assertThat(userAttributes.getAssignedTodos()).containsOnly(todoBack);

        userAttributes.setAssignedTodos(new HashSet<>());
        assertThat(userAttributes.getAssignedTodos()).doesNotContain(todoBack);
    }

    @Test
    void badgesTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Badge badgeBack = getBadgeRandomSampleGenerator();

        userAttributes.addBadges(badgeBack);
        assertThat(userAttributes.getBadges()).containsOnly(badgeBack);

        userAttributes.removeBadges(badgeBack);
        assertThat(userAttributes.getBadges()).doesNotContain(badgeBack);

        userAttributes.badges(new HashSet<>(Set.of(badgeBack)));
        assertThat(userAttributes.getBadges()).containsOnly(badgeBack);

        userAttributes.setBadges(new HashSet<>());
        assertThat(userAttributes.getBadges()).doesNotContain(badgeBack);
    }

    @Test
    void projectMembersTest() {
        UserAttributes userAttributes = getUserAttributesRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        userAttributes.addProjectMembers(projectBack);
        assertThat(userAttributes.getProjectMembers()).containsOnly(projectBack);

        userAttributes.removeProjectMembers(projectBack);
        assertThat(userAttributes.getProjectMembers()).doesNotContain(projectBack);

        userAttributes.projectMembers(new HashSet<>(Set.of(projectBack)));
        assertThat(userAttributes.getProjectMembers()).containsOnly(projectBack);

        userAttributes.setProjectMembers(new HashSet<>());
        assertThat(userAttributes.getProjectMembers()).doesNotContain(projectBack);
    }
}
