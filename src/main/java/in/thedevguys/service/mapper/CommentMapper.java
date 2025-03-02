package in.thedevguys.service.mapper;

import in.thedevguys.domain.Comment;
import in.thedevguys.domain.Todo;
import in.thedevguys.domain.UserAttributes;
import in.thedevguys.service.dto.CommentDTO;
import in.thedevguys.service.dto.TodoDTO;
import in.thedevguys.service.dto.UserAttributesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userAttributesId")
    @Mapping(target = "todo", source = "todo", qualifiedByName = "todoId")
    CommentDTO toDto(Comment s);

    @Named("userAttributesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserAttributesDTO toDtoUserAttributesId(UserAttributes userAttributes);

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);
}
