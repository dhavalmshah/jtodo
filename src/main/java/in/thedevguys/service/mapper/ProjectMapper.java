package in.thedevguys.service.mapper;

import in.thedevguys.domain.Project;
import in.thedevguys.domain.UserAttributes;
import in.thedevguys.service.dto.ProjectDTO;
import in.thedevguys.service.dto.UserAttributesDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userAttributesId")
    @Mapping(target = "members", source = "members", qualifiedByName = "userAttributesIdSet")
    ProjectDTO toDto(Project s);

    @Mapping(target = "members", ignore = true)
    @Mapping(target = "removeMembers", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    @Named("userAttributesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserAttributesDTO toDtoUserAttributesId(UserAttributes userAttributes);

    @Named("userAttributesIdSet")
    default Set<UserAttributesDTO> toDtoUserAttributesIdSet(Set<UserAttributes> userAttributes) {
        return userAttributes.stream().map(this::toDtoUserAttributesId).collect(Collectors.toSet());
    }
}
