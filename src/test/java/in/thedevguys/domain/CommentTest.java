package in.thedevguys.domain;

import static in.thedevguys.domain.CommentTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.UserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = getCommentSample1();
        Comment comment2 = new Comment();
        assertThat(comment1).isNotEqualTo(comment2);

        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);

        comment2 = getCommentSample2();
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    void userTest() {
        Comment comment = getCommentRandomSampleGenerator();
        User userAttributesBack = getUserAttributesRandomSampleGenerator();

        comment.setUser(userAttributesBack);
        assertThat(comment.getUser()).isEqualTo(userAttributesBack);

        comment.user(null);
        assertThat(comment.getUser()).isNull();
    }

    @Test
    void todoTest() {
        Comment comment = getCommentRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        comment.setTodo(todoBack);
        assertThat(comment.getTodo()).isEqualTo(todoBack);

        comment.todo(null);
        assertThat(comment.getTodo()).isNull();
    }
}
