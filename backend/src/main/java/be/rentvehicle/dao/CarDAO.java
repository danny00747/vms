package be.rentvehicle.dao;

import be.rentvehicle.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Car} entity.
 */
public interface CarDAO extends JpaRepository<Car, UUID> {

    @Query("select car from Car car order by car.madeInYear")
    List<Car> findAllWithEagerRelationships();
}
