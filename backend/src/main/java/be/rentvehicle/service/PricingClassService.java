package be.rentvehicle.service;

import be.rentvehicle.domain.PricingClass;
import be.rentvehicle.service.dto.PricingClassDTO;

import java.util.List;
import java.util.Optional;

/**
 * PricingClassService interface for the {@link PricingClass} entity.
 */
public interface PricingClassService {

    /**
     * Get all the entities.
     *
     * @return the list of entities.
     */
    List<PricingClassDTO> getAll();

    /**
     * Updates fields for a specific class name, and return the modified pricingDTO.
     *
     * @param className class to update.
     * @param pricingClassDTO containing fields to update.
     * @return updated pricingClassDTO.
     */
    Optional<PricingClassDTO> updatePricingClass(String className, PricingClassDTO pricingClassDTO);


}
