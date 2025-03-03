package in.thedevguys.service.mapper;

import in.thedevguys.domain.Project;
import in.thedevguys.domain.Tag;
import in.thedevguys.domain.Todo;
import in.thedevguys.domain.User;
import in.thedevguys.service.dto.ProjectDTO;
import in.thedevguys.service.dto.TagDTO;
import in.thedevguys.service.dto.TodoDTO;
import in.thedevguys.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Todo} and its DTO {@link TodoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TodoMapper extends EntityMapper<TodoDTO, Todo> {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagIdSet")
    @Mapping(target = "creator", source = "creator", qualifiedByName = "userId")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "todoId")
    @Mapping(target = "assignedUsers", source = "assignedUsers", qualifiedByName = "userIdSet")
    TodoDTO toDto(Todo s);

    @Mapping(target = "removeTags", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    @Mapping(target = "removeAssignedUsers", ignore = true)
    Todo toEntity(TodoDTO todoDTO);

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDTO toDtoTagId(Tag tag);

    @Named("tagIdSet")
    default Set<TagDTO> toDtoTagIdSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagId).collect(Collectors.toSet());
    }

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<UserDTO> toDtoUserIdSet(Set<User> users) {
        return users.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);
}
