package in.thedevguys.domain;

import static in.thedevguys.domain.ProjectTestSamples.*;
import static in.thedevguys.domain.TodoTestSamples.*;
import static in.thedevguys.domain.UserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.thedevguys.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = getProjectSample1();
        Project project2 = new Project();
        assertThat(project1).isNotEqualTo(project2);

        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);

        project2 = getProjectSample2();
        assertThat(project1).isNotEqualTo(project2);
    }

    @Test
    void todosTest() {
        Project project = getProjectRandomSampleGenerator();
        Todo todoBack = getTodoRandomSampleGenerator();

        project.addTodos(todoBack);
        assertThat(project.getTodos()).containsOnly(todoBack);
        assertThat(todoBack.getProject()).isEqualTo(project);

        project.removeTodos(todoBack);
        assertThat(project.getTodos()).doesNotContain(todoBack);
        assertThat(todoBack.getProject()).isNull();

        project.todos(new HashSet<>(Set.of(todoBack)));
        assertThat(project.getTodos()).containsOnly(todoBack);
        assertThat(todoBack.getProject()).isEqualTo(project);

        project.setTodos(new HashSet<>());
        assertThat(project.getTodos()).doesNotContain(todoBack);
        assertThat(todoBack.getProject()).isNull();
    }

    @Test
    void ownerTest() {
        Project project = getProjectRandomSampleGenerator();
        User userAttributesBack = getUserAttributesRandomSampleGenerator();

        project.setOwner(userAttributesBack);
        assertThat(project.getOwner()).isEqualTo(userAttributesBack);

        project.owner(null);
        assertThat(project.getOwner()).isNull();
    }

    @Test
    void membersTest() {
        Project project = getProjectRandomSampleGenerator();
        User userAttributesBack = getUserAttributesRandomSampleGenerator();

        project.addMembers(userAttributesBack);
        assertThat(project.getMembers()).containsOnly(userAttributesBack);
        assertThat(userAttributesBack.getProjectMembers()).containsOnly(project);

        project.removeMembers(userAttributesBack);
        assertThat(project.getMembers()).doesNotContain(userAttributesBack);
        assertThat(userAttributesBack.getProjectMembers()).doesNotContain(project);

        project.members(new HashSet<>(Set.of(userAttributesBack)));
        assertThat(project.getMembers()).containsOnly(userAttributesBack);
        assertThat(userAttributesBack.getProjectMembers()).containsOnly(project);

        project.setMembers(new HashSet<>());
        assertThat(project.getMembers()).doesNotContain(userAttributesBack);
        assertThat(userAttributesBack.getProjectMembers()).doesNotContain(project);
    }
}
