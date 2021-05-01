package be.rentvehicle.service;

import be.rentvehicle.domain.Booking;
import be.rentvehicle.service.dto.BookingDTO;
import be.rentvehicle.service.dto.UserInfoDTO;

import javax.validation.Valid;
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

    /**
     * Save a reservation.
     *
     * @param bookingDTO the entity to save.
     * @return the persisted entity.
     */
    BookingDTO save(@Valid BookingDTO bookingDTO, String cardId);
}
