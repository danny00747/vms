package be.rentvehicle.dao;

import be.rentvehicle.domain.Town;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Town entity.
 */
@Repository
public interface TownDAO extends JpaRepository<Town, Integer> {
}
