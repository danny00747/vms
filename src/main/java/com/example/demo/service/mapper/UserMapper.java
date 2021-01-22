package com.example.demo.service.mapper;

import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import com.example.demo.service.dto.TeacherDTO;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring")
public non-sealed interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mappings({
            @Mapping(source = "roles", target = "roles"),
            @Mapping(source = "userId", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "userEmail", target = "email"),
            @Mapping(source = "password", target = "password")
    })
    User toEntity(UserDTO userDTO);

    @Mappings({
            @Mapping(source = "roles", target = "roles"),
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "email", target = "userEmail"),
            @Mapping(source = "password", target = "password")
    })
    UserDTO toDto(User user);

    default User fromId(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
