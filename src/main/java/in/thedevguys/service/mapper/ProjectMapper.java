package in.thedevguys.service.mapper;

import in.thedevguys.domain.Project;
import in.thedevguys.domain.User;
import in.thedevguys.service.dto.AdminUserDTO;
import in.thedevguys.service.dto.ProjectDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    @Mapping(target = "members", source = "members", qualifiedByName = "userIdSet")
    ProjectDTO toDto(Project s);

    @Mapping(target = "members", ignore = true)
    @Mapping(target = "removeMembers", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminUserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<AdminUserDTO> toDtoUserIdSet(Set<User> users) {
        return users.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }
}
