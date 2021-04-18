package be.rentvehicle.service;

import be.rentvehicle.domain.Model;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.dto.ModelDTO;

import java.util.List;
import java.util.Optional;

/**
 * UserService interface for the {@link Model} entity.
 */
public interface ModelService {

    /**
     * Get all the entities.
     *
     * @return the list of entities.
     */
    List<ModelDTO> findAll();

    /**
     * Partially updates a model.
     *
     * @param modelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModelDTO> partialUpdate(ModelDTO modelDTO);

}
