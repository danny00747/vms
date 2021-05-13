package be.rentvehicle.dao;

import be.rentvehicle.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Rent} entity.
 */
@Repository
public interface RentDAO extends JpaRepository<Rent, UUID> {
}
