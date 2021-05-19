package be.rentvehicle.service;

import be.rentvehicle.domain.User;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.dto.UserInfoDTO;

import javax.validation.Valid;
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
    UserInfoDTO registerUser(@Valid UserInfoDTO userDTO);

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
     * @return a user by their JWT.
     */
    Optional<UserInfoDTO> getUserWithJwt();

    /**
     * Gets a user.
     * @param username to search.
     * @return a user .
     */
    Optional<UserInfoDTO> getByUsername(String username);

    /**
     * Update basic information (username, email) for a specific user, and return the modified user.
     *
     * @param usernameParam username of user to update.
     * @param userDTO containing fields to update.
     * @return updated user.
     */
    Optional<UserInfoDTO> updateUser(String usernameParam, UserInfoDTO userDTO);

    /**
     * Deletes a specific user, and return a confirmation message.
     *
     * @param username username of user.
     * @return confirmation message.
     */
    Optional<String> deleteUser(String username);

    /**
     * Deletes specific users, and return a confirmation message.
     *
     * @param ids list of user ids to delete.
     * @return confirmation message.
     */
    Optional<String> bulkDeleteUsers(List<String> ids);


    /**
     * Deletes a specific user, and return a confirmation message.
     *
     * @param key activation key.
     * @return confirmation message.
     */
    Optional<String> activateRegistration(String key);

    /**
     * Verifies a specific phone number.
     *
     * @param verificationCode code to verify.
     * @return confirmation message.
     */
    Optional<String> verifyPhoneNumber(Integer verificationCode);


    /**
     * Not activated users should be automatically deleted after 3 days.
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    void removeNotActivatedUsers();
}
