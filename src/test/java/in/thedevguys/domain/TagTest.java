package in.thedevguys.domain;

import static in.thedevguys.domain.TagTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void todosTest() {
        Tag tag = getTagRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        tag.addTodos(todoBack);
        assertThat(tag.getTodos()).containsOnly(todoBack);
        assertThat(todoBack.getTags()).containsOnly(tag);

        tag.removeTodos(todoBack);
        assertThat(tag.getTodos()).doesNotContain(todoBack);
        assertThat(todoBack.getTags()).doesNotContain(tag);

        tag.todos(new HashSet<>(Set.of(todoBack)));
        assertThat(tag.getTodos()).containsOnly(todoBack);
        assertThat(todoBack.getTags()).containsOnly(tag);

        tag.setTodos(new HashSet<>());
        assertThat(tag.getTodos()).doesNotContain(todoBack);
        assertThat(todoBack.getTags()).doesNotContain(tag);
    }
}
