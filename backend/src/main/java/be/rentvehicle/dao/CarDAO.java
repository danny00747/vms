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

    @Query(
            value = """
                    SELECT license_plate, model_type, brand, bags_number FROM cars car 
                    JOIN models model ON model.id = car.model_id
                    JOIN cars_models_options cmo on model.id = cmo.model_id
                    JOIN models_options mo on cmo.option_code = mo.option_code
                    """,
            nativeQuery = true)
    List<Object[]> findAllWithEagerRelationships();


}
