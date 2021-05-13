package be.rentvehicle.service;

import be.rentvehicle.domain.Booking;
import be.rentvehicle.service.dto.BookingDTO;
import be.rentvehicle.service.dto.UserInfoDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
     * @param bookingDTO the booking to save.
     * @param cardId the id of the car.
     * @return the persisted entity.
     */
    BookingDTO save(@Valid BookingDTO bookingDTO, String cardId);

    /**
     * Deletes a specific reservation, and return a confirmation message.
     *
     * @param bookingId id of the reservation.
     * @return confirmation message.
     */
    Optional<String> deleteBooking(String bookingId);

}
