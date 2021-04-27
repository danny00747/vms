package be.rentvehicle.service;

import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.dto.UserInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * UserService interface for the {@link User} entity.
 */
public interface UserService {

    /**
     * Save a user.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    UserDTO registerUser(UserDTO userDTO);

    /**
     * Get all the users with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    List<UserDTO> findAllWithEagerRelationships();

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<UserInfoDTO> findAll();

    /**
     * Gets a list of all the roles.
     *
     * @return a list of all the roles.
     */
    List<String> getRoles();


    /**
     * Gets a user.
     *
     * @return a user .
     */
    Optional<User> getUserWithRoles();

    /**
     * Gets a user.
     *
     * @return a user .
     */
    Optional<UserInfoDTO> getByUsername(String username);

    /**
     * Update basic information (username, email) for a specific user, and return the modified user.
     *
     * @param username username of user.
     * @param email    email of user.
     * @return updated user.
     */
    Optional<User> updateUser(String usernameParam, String username, String email);

    /**
     * Deletes a specific user, and return a confirmation message.
     *
     * @param username username of user.
     * @return confirmation message.
     */
    Optional<String> deleteUser(String username);

    /**
     * Deletes a specific user, and return a confirmation message.
     *
     * @param key activation key.
     * @return confirmation message.
     */
    Optional<String> activateRegistration(String key);

    /**
     * Not activated users should be automatically deleted after 3 days.
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    void removeNotActivatedUsers();
}
