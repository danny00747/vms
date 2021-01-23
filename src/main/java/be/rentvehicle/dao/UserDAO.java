package be.rentvehicle.dao;

import be.rentvehicle.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByUsername(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByUsername(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByEmailIgnoreCase(String email);

    Page<User> findAllByUsernameNot(Pageable pageable, String username);

    @Query("select distinct user from User user left join fetch user.roles order by user.username")
    List<User> findAllWithEagerRelationships();
}
