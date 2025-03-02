package in.thedevguys.service.mapper;

import in.thedevguys.domain.Badge;
import in.thedevguys.domain.Project;
import in.thedevguys.domain.Todo;
import in.thedevguys.domain.User;
import in.thedevguys.domain.UserAttributes;
import in.thedevguys.service.dto.BadgeDTO;
import in.thedevguys.service.dto.ProjectDTO;
import in.thedevguys.service.dto.TodoDTO;
import in.thedevguys.service.dto.UserAttributesDTO;
import in.thedevguys.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAttributes} and its DTO {@link UserAttributesDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserAttributesMapper extends EntityMapper<UserAttributesDTO, UserAttributes> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "assignedTodos", source = "assignedTodos", qualifiedByName = "todoIdSet")
    @Mapping(target = "badges", source = "badges", qualifiedByName = "badgeIdSet")
    @Mapping(target = "projectMembers", source = "projectMembers", qualifiedByName = "projectIdSet")
    UserAttributesDTO toDto(UserAttributes s);

    @Mapping(target = "removeAssignedTodos", ignore = true)
    @Mapping(target = "removeBadges", ignore = true)
    @Mapping(target = "removeProjectMembers", ignore = true)
    UserAttributes toEntity(UserAttributesDTO userAttributesDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);

    @Named("projectIdSet")
    default Set<ProjectDTO> toDtoProjectIdSet(Set<Project> project) {
        return project.stream().map(this::toDtoProjectId).collect(Collectors.toSet());
    }

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);

    @Named("todoIdSet")
    default Set<TodoDTO> toDtoTodoIdSet(Set<Todo> todo) {
        return todo.stream().map(this::toDtoTodoId).collect(Collectors.toSet());
    }

    @Named("badgeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BadgeDTO toDtoBadgeId(Badge badge);

    @Named("badgeIdSet")
    default Set<BadgeDTO> toDtoBadgeIdSet(Set<Badge> badge) {
        return badge.stream().map(this::toDtoBadgeId).collect(Collectors.toSet());
    }
}
