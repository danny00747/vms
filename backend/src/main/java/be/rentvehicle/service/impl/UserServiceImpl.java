package be.rentvehicle.service.impl;

import be.rentvehicle.dao.AddressDAO;
import be.rentvehicle.dao.TownDAO;
import be.rentvehicle.domain.Address;
import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.Town;
import be.rentvehicle.domain.User;
import be.rentvehicle.security.SecurityUtils;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.mapper.UserMapper;
import be.rentvehicle.dao.RolesDAO;
import be.rentvehicle.dao.UserDAO;
import be.rentvehicle.security.RolesConstants;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final AddressDAO addressDAO;
    private final TownDAO townDAO;
    private final RolesDAO rolesDAO;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, AddressDAO addressDAO, TownDAO townDAO, RolesDAO rolesDAO, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.townDAO = townDAO;
        this.rolesDAO = rolesDAO;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        userDAO.findOneByUsername(userDTO.getUsername().toLowerCase())
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyUsedException(existingUser.getUsername());
                });
        userDAO.findOneByEmailIgnoreCase(userDTO.getUserEmail())
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyUsedException(existingUser.getEmail());
                });

        User user = userMapper.toEntity(userDTO);
        Set<Roles> roles = new HashSet<>();
        rolesDAO.findById(RolesConstants.USER).ifPresent(roles::add);
        user.setRoles(roles);
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);

        saveTown(userDTO, user);

        user = userDAO.save(user);

        log.info("Created Information for User: {}", user);
        return userMapper.toDto(user);
    }

    private Address saveAddress(UserDTO userDTO, User user) {

        Address address = new Address();
        address.setRoad(userDTO.getAddress().getRoad());
        address.setHouseNumber(userDTO.getAddress().getHouseNumber());
        address.setPostBox(userDTO.getAddress().getPostBox());

        user.setAddress(address);

        address.setUser(user);

        addressDAO.save(address);

        return address;
    }

    private void saveTown(UserDTO userDTO, User user) {

        Address address = saveAddress(userDTO, user);

        Set<Address> addresses = Set.of(address);

        final Town town = new Town();
        town.setPostcode(userDTO.getTown().getPostcode());
        townDAO.findById(userDTO.getTown().getPostcode())
                .ifPresentOrElse(
                foundTown -> town.setName(foundTown.getName()),
                () -> town.setName(userDTO.getTown().getName())
        );
        town.setAddresses(addresses);
        Town savedTown = townDAO.save(town);
        address.setTown(savedTown);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userDAO.findAllWithEagerRelationships()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getRoles() {
        return rolesDAO.findAll()
                .stream()
                .map(Roles::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserWithRoles() {
        return SecurityUtils.getCurrentAuthenticatedUser().flatMap(userDAO::findOneWithRolesByUsername);
    }

    @Override
    public Optional<User> getUserWithRolesByUsername(String username) {
        return userDAO.findOneWithRolesByUsername(username);
    }

    @Override
    public Optional<User> updateUser(String usernameParam, String username, String email) {

        Optional<User> existingUserEmail = userDAO.findOneByEmailIgnoreCase(email);
        if (existingUserEmail.isPresent() && (!existingUserEmail.get().getUsername().equalsIgnoreCase(usernameParam))) {
            throw new EmailAlreadyUsedException(email);
        }
        Optional<User> existingUsername = userDAO.findOneByUsername(username);
        if (existingUsername.isPresent() && (!existingUsername.get().getUsername().equalsIgnoreCase(usernameParam))) {
            throw new UsernameAlreadyUsedException(username);
        }

        return Optional.of(userDAO
                .findOneByUsername(usernameParam))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setUsername(username);
                    user.setEmail(email);
                    userDAO.save(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                });
    }

    @Override
    public Optional<String> deleteUser(String username) {
        return Optional.of(userDAO
                .findOneByUsername(username))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    userDAO.delete(user);
                    return "This user '" + user.getUsername() + "' was successfully deleted !";
                });
    }

    @Override
    public List<UserDTO> findAllWithEagerRelationships() {
        return null;
    }
}
