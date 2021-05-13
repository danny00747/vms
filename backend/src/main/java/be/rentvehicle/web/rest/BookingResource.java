package be.rentvehicle.web.rest;

import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.service.BookingService;
import be.rentvehicle.service.dto.BookingDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.ResourceFoundException;
import be.rentvehicle.service.impl.errors.UserNotFoundException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Book;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing the bookings
 */
@Controller
@Validated
public class BookingResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private final BookingService bookingService;

    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * {@code GET  /bookings} : get all the bookings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/bookings")
    @isAdmin
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        log.debug("REST request to get all bookings");
        List<BookingDTO> bookingDTOList = this.bookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTOList);
    }

    /**
     * {@code POST  /register} : register a reservation.
     *
     * @param bookingDTO the reservation to create.
     * @param carId      the car to book.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservation, or with status {@code 400 (Bad Request)} if something went wrong.
     */
    @PostMapping("/bookings/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookingDTO> createReservation(@RequestBody @Valid BookingDTO bookingDTO, @PathVariable String carId) {
        log.debug("REST request to create a reservation");
        BookingDTO createdReservation = this.bookingService.save(bookingDTO, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }


    /**
     * {@code DELETE /bookings/:bookingId} : delete the a booking.
     *
     * @param bookingId the id of a booking to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     * @throws ResourceFoundException {@code 404 (Not Found)} if the booking couldn't be found.
     */
    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable String bookingId) {
        log.debug("REST request to delete Booking : {}", bookingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", bookingService.deleteBooking(bookingId)
                        .orElseThrow(() -> new ResourceFoundException("No booking was found with this id :" + bookingId))));
    }
}
