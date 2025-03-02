package in.thedevguys.service.mapper;

import in.thedevguys.domain.Badge;
import in.thedevguys.domain.UserAttributes;
import in.thedevguys.service.dto.BadgeDTO;
import in.thedevguys.service.dto.UserAttributesDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userAttributesIdSet")
    BadgeDTO toDto(Badge s);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    Badge toEntity(BadgeDTO badgeDTO);

    @Named("userAttributesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserAttributesDTO toDtoUserAttributesId(UserAttributes userAttributes);

    @Named("userAttributesIdSet")
    default Set<UserAttributesDTO> toDtoUserAttributesIdSet(Set<UserAttributes> userAttributes) {
        return userAttributes.stream().map(this::toDtoUserAttributesId).collect(Collectors.toSet());
    }
}
