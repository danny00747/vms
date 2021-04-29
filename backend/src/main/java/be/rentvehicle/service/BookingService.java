package be.rentvehicle.service;

import be.rentvehicle.domain.Booking;
import be.rentvehicle.service.dto.BookingDTO;

import java.util.List;

/**
 * BookingService interface for the {@link Booking} entity.
 */
public interface BookingService {

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<BookingDTO> findAll();
}
