package be.rentvehicle.dao;

import be.rentvehicle.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}


/*
select cja.purchased_price,
       cja.created_at,
       (select json_agg(cars)
        from (
                 select a.license_plate,
                        a.made_in_year,
                        (select json_agg(mdl)
                         from (
                                  select m.brand,
                                         m.model_type,
                                         (select json_agg(opt)
                                          from (
                                                   select mo.bags_number, mo.is_automatic, mo.has_air_conditioner
                                                   from cars_models_options cmo
                                                            join models_options mo on cmo.option_code = mo.option_code
                                               ) opt
                                         ) as options
                                  from models m
                                           join cars c on m.id = c.model_id
                                  where brand = 'Ford'
                              ) mdl
                        ) as models
                 from cars as a) cars)
from cars as cja;

 */
