package in.thedevguys.service.mapper;

import static in.thedevguys.domain.TodoAsserts.*;
import static in.thedevguys.domain.TodoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoMapperTest {

    private TodoMapper todoMapper;

    @BeforeEach
    void setUp() {
        todoMapper = new TodoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTodoSample1();
        var actual = todoMapper.toEntity(todoMapper.toDto(expected));
        assertTodoAllPropertiesEquals(expected, actual);
    }
}
