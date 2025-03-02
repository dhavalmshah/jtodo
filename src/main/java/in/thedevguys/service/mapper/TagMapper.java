package in.thedevguys.service.mapper;

import in.thedevguys.domain.Tag;
import in.thedevguys.domain.Todo;
import in.thedevguys.service.dto.TagDTO;
import in.thedevguys.service.dto.TodoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "todos", source = "todos", qualifiedByName = "todoIdSet")
    TagDTO toDto(Tag s);

    @Mapping(target = "todos", ignore = true)
    @Mapping(target = "removeTodos", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);

    @Named("todoIdSet")
    default Set<TodoDTO> toDtoTodoIdSet(Set<Todo> todo) {
        return todo.stream().map(this::toDtoTodoId).collect(Collectors.toSet());
    }
}
