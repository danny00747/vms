package be.rentvehicle.service.mapper;

import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 * Normal mappers are generated using MapStruct, this one is hand-coded as roles
 * in UserDTO are a different type compared to roles in User class.
 */

@Service
public class UserMapper {

    public List<UserDTO> toDto(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO toDto(User user) {
        return new UserDTO(user);
    }

    public List<User> toEntity(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getUserId());
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getUserEmail());
            user.setPassword(userDTO.getPassword());
            user.setActivated(userDTO.isActivated());
            Set<Roles> roles = this.rolesFromStrings(userDTO.getUserRoles());
            user.setRoles(roles);
            return user;
        }
    }

    private Set<Roles> rolesFromStrings(Set<String> rolesAsString) {
        Set<Roles> roles = new HashSet<>();

        if (rolesAsString != null) {
            roles = rolesAsString.stream()
                    .map(string -> {
                Roles auth = new Roles();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }
        return roles;
    }

    public User userFromId(UUID id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
