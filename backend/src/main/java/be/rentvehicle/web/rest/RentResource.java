package be.rentvehicle.web.rest;

import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.service.RentService;
import be.rentvehicle.service.dto.RentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing the Rent entity.
 */
@Controller
@Validated
public class RentResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(RentResource.class);

    private final RentService rentService;

    public RentResource(RentService rentService) {
        this.rentService = rentService;
    }

    /**
     * {@code GET  /rents} : get all the rents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rents in body.
     */
    @GetMapping("/rents")
    @isAdmin
    public ResponseEntity<List<RentDTO>> getAllRents() {
        log.debug("REST request to get all rents");
        List<RentDTO> rents = this.rentService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rents);
    }


    /**
     * {@code POST  /register} : register a rent.
     *
     * @param rentDTO   the rent to create.
     * @param bookingId the id of the reservation.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rent, or with status {@code 400 (Bad Request)} if something went wrong.
     */
    @PostMapping("/rents/{bookingId}")
    @ResponseStatus(HttpStatus.CREATED)
    @isAdmin
    public ResponseEntity<RentDTO> createRent(@RequestBody @Valid RentDTO rentDTO, @PathVariable String bookingId) {
        log.debug("REST request to create a rent");
        RentDTO createdRent = this.rentService.save(rentDTO, bookingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRent);
    }
}
