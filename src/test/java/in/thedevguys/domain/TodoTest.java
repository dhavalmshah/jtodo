package in.thedevguys.domain;

import static in.thedevguys.domain.AttachmentTestSamples.*;
import static in.thedevguys.domain.CommentTestSamples.*;
import static in.thedevguys.domain.NotificationTestSamples.*;
import static in.thedevguys.domain.ProjectTestSamples.*;
import static in.thedevguys.domain.TagTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.UserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TodoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Todo.class);
        Todo todo1 = getTodoSample1();
        Todo todo2 = new Todo();
        assertThat(todo1).isNotEqualTo(todo2);

        todo2.setId(todo1.getId());
        assertThat(todo1).isEqualTo(todo2);

        todo2 = getTodoSample2();
        assertThat(todo1).isNotEqualTo(todo2);
    }

    @Test
    void subTasksTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        todo.addSubTasks(todoBack);
        assertThat(todo.getSubTasks()).containsOnly(todoBack);
        assertThat(todoBack.getParent()).isEqualTo(todo);

        todo.removeSubTasks(todoBack);
        assertThat(todo.getSubTasks()).doesNotContain(todoBack);
        assertThat(todoBack.getParent()).isNull();

        todo.subTasks(new HashSet<>(Set.of(todoBack)));
        assertThat(todo.getSubTasks()).containsOnly(todoBack);
        assertThat(todoBack.getParent()).isEqualTo(todo);

        todo.setSubTasks(new HashSet<>());
        assertThat(todo.getSubTasks()).doesNotContain(todoBack);
        assertThat(todoBack.getParent()).isNull();
    }

    @Test
    void commentsTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        todo.addComments(commentBack);
        assertThat(todo.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getTodo()).isEqualTo(todo);

        todo.removeComments(commentBack);
        assertThat(todo.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getTodo()).isNull();

        todo.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(todo.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getTodo()).isEqualTo(todo);

        todo.setComments(new HashSet<>());
        assertThat(todo.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getTodo()).isNull();
    }

    @Test
    void attachmentsTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        todo.addAttachments(attachmentBack);
        assertThat(todo.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getTodo()).isEqualTo(todo);

        todo.removeAttachments(attachmentBack);
        assertThat(todo.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getTodo()).isNull();

        todo.attachments(new HashSet<>(Set.of(attachmentBack)));
        assertThat(todo.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getTodo()).isEqualTo(todo);

        todo.setAttachments(new HashSet<>());
        assertThat(todo.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getTodo()).isNull();
    }

    @Test
    void notificationsTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        todo.addNotifications(notificationBack);
        assertThat(todo.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getTask()).isEqualTo(todo);

        todo.removeNotifications(notificationBack);
        assertThat(todo.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getTask()).isNull();

        todo.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(todo.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getTask()).isEqualTo(todo);

        todo.setNotifications(new HashSet<>());
        assertThat(todo.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getTask()).isNull();
    }

    @Test
    void tagsTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        todo.addTags(tagBack);
        assertThat(todo.getTags()).containsOnly(tagBack);

        todo.removeTags(tagBack);
        assertThat(todo.getTags()).doesNotContain(tagBack);

        todo.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(todo.getTags()).containsOnly(tagBack);

        todo.setTags(new HashSet<>());
        assertThat(todo.getTags()).doesNotContain(tagBack);
    }

    @Test
    void creatorTest() {
        Todo todo = getTodoRandomSampleGenerator();
        User userAttributesBack = getUserAttributesRandomSampleGenerator();

        todo.setCreator(userAttributesBack);
        assertThat(todo.getCreator()).isEqualTo(userAttributesBack);

        todo.creator(null);
        assertThat(todo.getCreator()).isNull();
    }

    @Test
    void projectTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        todo.setProject(projectBack);
        assertThat(todo.getProject()).isEqualTo(projectBack);

        todo.project(null);
        assertThat(todo.getProject()).isNull();
    }

    @Test
    void parentTest() {
        Todo todo = getTodoRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        todo.setParent(todoBack);
        assertThat(todo.getParent()).isEqualTo(todoBack);

        todo.parent(null);
        assertThat(todo.getParent()).isNull();
    }

    @Test
    void assignedUsersTest() {
        Todo todo = getTodoRandomSampleGenerator();
        User userAttributesBack = getUserAttributesRandomSampleGenerator();

        todo.addAssignedUsers(userAttributesBack);
        assertThat(todo.getAssignedUsers()).containsOnly(userAttributesBack);
        assertThat(userAttributesBack.getAssignedTodos()).containsOnly(todo);

        todo.removeAssignedUsers(userAttributesBack);
        assertThat(todo.getAssignedUsers()).doesNotContain(userAttributesBack);
        assertThat(userAttributesBack.getAssignedTodos()).doesNotContain(todo);

        todo.assignedUsers(new HashSet<>(Set.of(userAttributesBack)));
        assertThat(todo.getAssignedUsers()).containsOnly(userAttributesBack);
        assertThat(userAttributesBack.getAssignedTodos()).containsOnly(todo);

        todo.setAssignedUsers(new HashSet<>());
        assertThat(todo.getAssignedUsers()).doesNotContain(userAttributesBack);
        assertThat(userAttributesBack.getAssignedTodos()).doesNotContain(todo);
    }
}
