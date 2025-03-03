package in.thedevguys.service.mapper;

import in.thedevguys.domain.Attachment;
import in.thedevguys.domain.Todo;
import in.thedevguys.domain.User;
import in.thedevguys.service.dto.AttachmentDTO;
import in.thedevguys.service.dto.TodoDTO;
import in.thedevguys.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "uploader", source = "uploader", qualifiedByName = "userId")
    @Mapping(target = "todo", source = "todo", qualifiedByName = "todoId")
    AttachmentDTO toDto(Attachment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("todoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TodoDTO toDtoTodoId(Todo todo);
}
