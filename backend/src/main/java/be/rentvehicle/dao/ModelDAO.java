package be.rentvehicle.dao;

import be.rentvehicle.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Model} entity.
 */
public interface ModelDAO extends JpaRepository<Model, UUID> {
}
