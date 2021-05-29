package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.UserInfoDTO;
import org.mapstruct.*;

import java.util.UUID;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {BookingMapper.class, AddressMapper.class, RoleMapper.class}, imports = {UUID.class})
public non-sealed interface UserInfoMapper extends EntityMapper<UserInfoDTO, User> {

    @Mappings({
            @Mapping(expression = "java(user.getId().toString())", target = "userId"),
            @Mapping(source = "email", target = "userEmail"),
            @Mapping(source = "user.roles", target = "userRoles"),
            @Mapping(source = "user.address", target = "addressDTO"),
            @Mapping(source = "user.booking", target = "bookingDTO")
    })
    UserInfoDTO toDto(User user);

    @Mappings({
            @Mapping(source = "addressDTO", target = "address"),
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "userEmail", target = "email"),
    })
    User toEntity(UserInfoDTO userInfoDTO);

    @Mappings({
            @Mapping(target = "activated", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(source = "userEmail", target = "email"),
    })
    void partialUpdate(@MappingTarget User user, UserInfoDTO userInfoDTO);


/*
    // @Mapping(target = "userId", source = "id"),
    default User setId(UUID id) {
        if (id == null) {
            User user = new User();
            user.setId(UUID.randomUUID());
            return user;
        }
        User user = new User();
        user.setId(id);
        return user;
    }


    default UUID map(String id) {
        if (id == null) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserId(new User().getId().toString());
            return UUID.randomUUID();
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(UUID.fromString(id).toString());
        return UUID.fromString(id);
    }
 */

}
