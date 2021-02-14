package be.rentvehicle.service.mappers;


import be.rentvehicle.domain.Address;
import be.rentvehicle.domain.Town;
import be.rentvehicle.service.mapper.UserMapper;
import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link UserMapper}.
 */
public class UserMapperTest {

    private static final String DEFAULT_LOGIN = "test";
    private static final UUID DEFAULT_ID = UUID.randomUUID();

    private UserMapper userMapper;
    private User user;
    private UserDTO userDto;

    @BeforeEach
    public void init() {
        userMapper = new UserMapper();
        user = new User();
        user.setUsername(DEFAULT_LOGIN);
        user.setPassword(UUID.randomUUID().toString());
        user.setEmail("test@localhost.com");

        Address address = new Address();
        address.setRoad("rue de la place");
        address.setHouseNumber(7);
        address.setPostBox(7);

        Town town = new Town();
        town.setName("LLN");
        town.setPostcode(1338);
        address.setTown(town);
        user.setAddress(address);
        userDto = new UserDTO(user);
    }

    @Test
    @DisplayName("users to userDTOs should map only nonNull users")
    public void usersToUserDTOsShouldMapOnlyNonNullUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(null);

        List<UserDTO> userDTOS = userMapper.toDto(users);

        assertThat(userDTOS).isNotEmpty();
        assertThat(userDTOS).size().isEqualTo(1);
    }

    @Test
    @DisplayName("userDTOs to users should map only nonNull users")
    public void userDTOsToUsersShouldMapOnlyNonNullUsers() {
        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);
        usersDto.add(null);

        List<User> users = userMapper.toEntity(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
    }

    @Test
    @DisplayName("userDTOs To users with roles string should map to users with role domain")
    public void userDTOsToUsersWithRolesStringShouldMapToUsersWithRolesDomain() {
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        userDto.setUserRoles(roles);

        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<User> users = userMapper.toEntity(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getRoles()).isNotNull();
        assertThat(users.get(0).getRoles()).isNotEmpty();
        assertThat(users.get(0).getRoles().iterator().next().getName()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("userDTOs to users map with null roles string should return user with empty roles")
    public void userDTOsToUsersMapWithNullRolesStringShouldReturnUserWithEmptyRoles() {
        userDto.setUserRoles(null);

        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<User> users = userMapper.toEntity(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getRoles()).isNotNull();
        assertThat(users.get(0).getRoles()).isEmpty();
    }

    @Test
    @DisplayName("userDTO To User Map With Roles String Should Return User With Roles")
    public void userDTOToUserMapWithRolesStringShouldReturnUserWithRoles() {
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setUserRoles(authoritiesAsString);

        User user = userMapper.toEntity(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getRoles()).isNotNull();
        assertThat(user.getRoles()).isNotEmpty();
        assertThat(user.getRoles().iterator().next().getName()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("userDTO To User Map With Null Roles String Should Return User With Empty Roles")
    public void userDTOToUserMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities() {
        userDto.setUserRoles(null);

        User user = userMapper.toEntity(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getRoles()).isNotNull();
        assertThat(user.getRoles()).isEmpty();
    }

    @Test
    @DisplayName("test UserFromId ")
    public void testUserFromId() {
        assertThat(userMapper.userFromId(DEFAULT_ID).getId()).isEqualTo(DEFAULT_ID);
        assertThat(userMapper.userFromId(null).getId()).isNull();
    }

}
