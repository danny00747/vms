package be.rentvehicle.service;

import be.rentvehicle.domain.Model;
import be.rentvehicle.service.dto.ModelDTO;

import java.util.List;

/**
 * UserService interface for the {@link Model} entity.
 */
public interface ModelService {

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<ModelDTO> findAll();
}
