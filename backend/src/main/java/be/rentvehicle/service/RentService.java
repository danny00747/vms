package be.rentvehicle.service;

import be.rentvehicle.domain.Rent;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.dto.RentDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * RentService interface for the {@link Rent} entity.
 */
public interface RentService {

    /**
     * Save a rent.
     *
     * @param rentDTO the rent entity to save.
     * @param bookingId the id of the booking.
     * @return the persisted entity.
     */
    RentDTO save(@Valid RentDTO rentDTO, String bookingId);

    /**
     * Get all the entities.
     *
     * @return the list of entities.
     */
    List<RentDTO> findAll();


}
