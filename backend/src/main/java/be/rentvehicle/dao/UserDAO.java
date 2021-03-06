package be.rentvehicle.dao;

import be.rentvehicle.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserDAO extends JpaRepository<User, UUID> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByUsername(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByUsernameAndActivatedIsTrueAndVerificationPhoneCodeIsNull(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByEmailIgnoreCaseAndActivatedIsTrue(String email);

    Optional<User> findOneByActivationKey(String activationKey);

    Optional<User> findOneByVerificationPhoneCode(Integer verificationPhoneCode);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedAtBefore(Instant dateTime);

    List<User> findAllByCreatedAtBetween(Instant dateTime1, Instant dateTime2);

    Page<User> findAllByUsernameNot(Pageable pageable, String username);

    Optional<User> findOneByResetKey(String resetKey);

    @Transactional
    @Modifying
    @Query("delete from User u where u.id in :uuids")
    List<User> deleteUsersById(List<UUID> uuids);

    @Query(""" 
                select distinct user from User user 
                left join fetch user.roles 
                join fetch user.address 
                order by user.username 
            """)
    List<User> findAllWithEagerRelationships();
}
