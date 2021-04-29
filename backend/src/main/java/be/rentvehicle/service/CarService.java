package be.rentvehicle.service;

import be.rentvehicle.domain.Car;
import be.rentvehicle.service.dto.CarDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * UserService interface for the {@link Car} entity.
 */
public interface CarService {

    /**
     * Get all the entities.
     *
     * @return the list of entities.
     */
    List<CarDTO> findAll();

    /**
     * Get the entities given a model brand .
     *
     * @param brand the model brand to search.
     * @return the list of entities.
     */
    List<CarDTO> getAllByModelBrand(String brand);

    /**
     * Partially updates a car.
     *
     * @param carDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarDTO> partialUpdate(CarDTO carDTO);


    /**
     * Get a car given its id .
     *
     * @param id the id of car to search.
     * @return the found entity.
     */
    Optional<CarDTO> getOneCarById(String id);


    /**
     * Get all non booked cars given a date .
     *
     * @param date the withdrawalDate to search.
     * @return the found entities.
     */
    List<CarDTO> getBookedCars(Instant date);

    List<CarDTO> nativeQuery();
}
