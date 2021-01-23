package be.rentvehicle.service.impl;

import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.User;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final RolesDAO rolesDAO;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDAO userDAO, RolesDAO rolesDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.rolesDAO = rolesDAO;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        userDAO.findOneByUsername(userDTO.getUsername().toLowerCase())
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyUsedException(existingUser.getUsername());
        });
        userDAO.findOneByEmailIgnoreCase(userDTO.getUserEmail())
                .ifPresent(existingUser -> { throw new EmailAlreadyUsedException(existingUser.getEmail());
        });

        User user = userMapper.toEntity(userDTO);
        Set<Roles> roles = new HashSet<>();
        rolesDAO.findById(RolesConstants.USER).ifPresent(roles::add);
        user.setRoles(roles);
        user = userDAO.save(user);
        log.info("Created Information for User: {}", user);
        return userMapper.toDto(user);
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
    public List<UserDTO> findAllWithEagerRelationships() {
        return null;
    }
}
