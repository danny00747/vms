package be.rentvehicle.dao;

import be.rentvehicle.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the {@link Roles} entity.
 */
@Repository
public interface RolesDAO extends JpaRepository<Roles, String> {
}
