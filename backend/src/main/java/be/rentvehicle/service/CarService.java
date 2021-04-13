package be.rentvehicle.service;

import be.rentvehicle.domain.Car;
import be.rentvehicle.service.dto.CarDTO;

import java.util.List;

/**
 * UserService interface for the {@link Car} entity.
 */
public interface CarService {

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<CarDTO> findAll();
}
