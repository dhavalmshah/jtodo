package in.thedevguys.service.mapper;

import in.thedevguys.domain.Notification;
import in.thedevguys.domain.Todo;
import in.thedevguys.domain.User;
import in.thedevguys.service.dto.AdminUserDTO;
import in.thedevguys.service.dto.NotificationDTO;
import in.thedevguys.service.dto.TodoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "task", source = "task", qualifiedByName = "todoId")
    NotificationDTO toDto(Notification s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminUserDTO toDtoUserId(User user);

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);
}
