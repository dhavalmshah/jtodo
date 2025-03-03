package in.thedevguys.service.mapper;

import in.thedevguys.domain.Badge;
import in.thedevguys.domain.User;
import in.thedevguys.service.dto.AdminUserDTO;
import in.thedevguys.service.dto.BadgeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userIdSet")
    BadgeDTO toDto(Badge s);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    Badge toEntity(BadgeDTO badgeDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdminUserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<AdminUserDTO> toDtoUserIdSet(Set<User> users) {
        return users.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }
}
