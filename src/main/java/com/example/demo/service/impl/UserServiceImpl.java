package com.example.demo.service.impl;

import com.example.demo.dao.RolesDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.domain.Roles;
import com.example.demo.domain.User;
import com.example.demo.security.RolesConstants;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.service.impl.errors.EmailAlreadyUsedException;
import com.example.demo.service.impl.errors.UsernameAlreadyUsedException;
import com.example.demo.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.HashSet;
import java.util.Set;

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

        /*


         User user = userMapper.toEntity(userDTO);
        Set<Roles> roles = new HashSet<>();
        rolesDAO.findById(RolesConstants.USER).ifPresent(roles::add);
        user.setRoles(roles);
        user = userDAO.save(user);
        log.debug("Created Information for User: {}", user);
        return userMapper.toDto(user);
         */

        userDAO.findOneByUsername(userDTO.getUsername().toLowerCase())
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyUsedException(existingUser.getUsername());
        });
        userDAO.findOneByEmailIgnoreCase(userDTO.getUserEmail())
                .ifPresent(existingUser -> { throw new EmailAlreadyUsedException(existingUser.getEmail());
        });

        User user = userMapper.toEntity(userDTO);
        user = userDAO.save(user);
        log.info("Created Information for User: {}", user);
        return userMapper.toDto(user);
    }
}
