package be.rentvehicle.service;

import be.rentvehicle.domain.Car;
import be.rentvehicle.service.dto.CarDTO;

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

    List<CarDTO> getAllByModelBrand(String brand);

    Optional<CarDTO> getOneCarById(String id);

    /**
     * Get all the entities.
     *
     * @return the list of entities with a given model brand.
     */
    List<CarDTO> findByModelBrand(String brand);

    List<CarDTO> finds();
}
