package be.rentvehicle.web.rest;

import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.service.BookingService;
import be.rentvehicle.service.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * REST controller for managing the bookings
 */
@Controller
@Validated
public class BookingResource extends BaseRestController{

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
    public ResponseEntity<List<BookingDTO>> getAllBookings(){
        log.debug("REST request to get all bookings");
        List<BookingDTO> bookingDTOList = this.bookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTOList);
    }
}
