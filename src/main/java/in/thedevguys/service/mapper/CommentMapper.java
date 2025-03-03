package in.thedevguys.service.mapper;

import in.thedevguys.domain.Comment;
import in.thedevguys.domain.Todo;
import in.thedevguys.domain.User;
import in.thedevguys.service.dto.AdminUserDTO;
import in.thedevguys.service.dto.CommentDTO;
import in.thedevguys.service.dto.TodoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "todo", source = "todo", qualifiedByName = "todoId")
    CommentDTO toDto(Comment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminUserDTO toDtoUserId(User user);

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);
}
