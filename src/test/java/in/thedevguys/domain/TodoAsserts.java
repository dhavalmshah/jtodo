package in.thedevguys.domain;

import static in.thedevguys.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class TodoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTodoAllPropertiesEquals(Todo expected, Todo actual) {
        assertTodoAutoGeneratedPropertiesEquals(expected, actual);
        assertTodoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTodoAllUpdatablePropertiesEquals(Todo expected, Todo actual) {
        assertTodoUpdatableFieldsEquals(expected, actual);
        assertTodoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTodoAutoGeneratedPropertiesEquals(Todo expected, Todo actual) {
        assertThat(actual)
            .as("Verify Todo auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTodoUpdatableFieldsEquals(Todo expected, Todo actual) {
        assertThat(actual)
            .as("Verify Todo relevant properties")
            .satisfies(a -> assertThat(a.getTitle()).as("check title").isEqualTo(expected.getTitle()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
            .satisfies(a ->
                assertThat(a.getDueDate()).as("check dueDate").usingComparator(zonedDataTimeSameInstant).isEqualTo(expected.getDueDate())
            )
            .satisfies(a ->
                assertThat(a.getCreatedAt())
                    .as("check createdAt")
                    .usingComparator(zonedDataTimeSameInstant)
                    .isEqualTo(expected.getCreatedAt())
            )
            .satisfies(a ->
                assertThat(a.getUpdatedAt())
                    .as("check updatedAt")
                    .usingComparator(zonedDataTimeSameInstant)
                    .isEqualTo(expected.getUpdatedAt())
            )
            .satisfies(a -> assertThat(a.getStatus()).as("check status").isEqualTo(expected.getStatus()))
            .satisfies(a -> assertThat(a.getPriority()).as("check priority").isEqualTo(expected.getPriority()))
            .satisfies(a -> assertThat(a.getPointsAwarded()).as("check pointsAwarded").isEqualTo(expected.getPointsAwarded()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTodoUpdatableRelationshipsEquals(Todo expected, Todo actual) {
        assertThat(actual)
            .as("Verify Todo relationships")
            .satisfies(a -> assertThat(a.getTags()).as("check tags").isEqualTo(expected.getTags()))
            .satisfies(a -> assertThat(a.getCreator()).as("check creator").isEqualTo(expected.getCreator()))
            .satisfies(a -> assertThat(a.getProject()).as("check project").isEqualTo(expected.getProject()))
            .satisfies(a -> assertThat(a.getParent()).as("check parent").isEqualTo(expected.getParent()))
            .satisfies(a -> assertThat(a.getAssignedUsers()).as("check assignedUsers").isEqualTo(expected.getAssignedUsers()));
    }
}
