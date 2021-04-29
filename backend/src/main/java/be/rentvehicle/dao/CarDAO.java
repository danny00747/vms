package be.rentvehicle.dao;

import be.rentvehicle.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Car} entity.
 */
@Repository
public interface CarDAO extends JpaRepository<Car, UUID> {

    @Query(
            value = """
                    SELECT cast (car.id as varchar), license_plate, model_type, brand, bags_number FROM cars car
                    JOIN models model ON model.id = car.model_id
                    JOIN models_options mo on model.model_option = mo.option_code
                    WHERE model.brand = 'Ford'
                    """,
            nativeQuery = true)
    List<Object[]> findAllWithEagerRelationships();

    Optional<Car> findOneByIdAndModelIsNotNull(UUID id);

    Optional<Car> findOneById(UUID id);


    @Query(""" 
                select distinct car from Car car 
                left join fetch car.model model 
                where model.brand = :modelBrand
            """)
    List<Car> findAllByModelBrand(String modelBrand);

    List<Car> findAllByBookingIsNotNull();

    List<Car> findAllByBookingWithdrawalDateAndBookingReturnDate(Instant t1, Instant t2);

    @Query("""
            select distinct c from Car c
            left join c.booking b
            where :date between b.withdrawalDate and b.returnDate
            """)
    List<Car> findBetweens(Instant date);
}
