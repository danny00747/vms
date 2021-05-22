package be.rentvehicle.service;

import be.rentvehicle.domain.Car;
import be.rentvehicle.service.dto.CarDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * CarService interface for the {@link Car} entity.
 */
public interface CarService {

    /**
     * Get all the entities.
     *
     * @return the list of entities.
     */
    List<CarDTO> getAll();

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
     * @param carId the id of car to search.
     * @return the found entity.
     */
    Optional<CarDTO> getOneCarById(String carId);


    /**
     * Get all non booked cars given a date .
     *
     * @param date the withdrawalDate to search.
     * @return the found entities.
     */
    List<CarDTO> getBookedCarsByDate(Instant date);

    /**
     * Get all non booked cars .
     *
     * @return the found entities.
     */
    List<CarDTO> getBookedCars();


    /**
     * Saves all damaged cars
     *
     * @param ids list of cars damaged cars
     * @return confirmation message.
     */
    Optional<String> saveDamagedCars(List<String> ids);
}
