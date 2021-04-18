package be.rentvehicle.dao;

import be.rentvehicle.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Model} entity.
 */
@Repository
public interface ModelDAO extends JpaRepository<Model, UUID> {

    Optional<Model> findByIdAndModelOptionIsNotNull(UUID id);
}
