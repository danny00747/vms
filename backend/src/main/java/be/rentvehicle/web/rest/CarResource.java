package be.rentvehicle.web.rest;

import be.rentvehicle.security.RolesConstants;
import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.impl.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Controller
@Validated
public class CarResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(CarResource.class);

    private final CarService carService;

    public CarResource(CarService carService) {
        this.carService = carService;
    }

    /**
     * {@code GET  /admin/cars} : get all the cars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/admin/cars")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.getAll());
    }

    /**
     * {@code GET  /cars} : get all none booked cars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> getNoneBookedCars(@RequestParam(required = false) String brand,
                                                          @RequestParam(required = false) String withdrawalDate) {

        if (brand != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(carService.getAllByModelBrand(brand));
        } else if (withdrawalDate != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(carService.getBookedCarsByDate(Instant.parse(withdrawalDate)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(carService.getBookedCars());
        }
    }

    /**
     * {@code GET /cars/:id} : get the "id" car.
     *
     * @param id the id of the car to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the found car or with status {@code 404 (Not Found)}.
     * @throws IllegalArgumentException {@code 400 (Bad Request)} if the id is an invalid UUID.
     * @throws ResourceNotFoundException   {@code 404 (Not Found)} if the car couldn't be returned.
     */
    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") String id) {
        log.debug("REST request to get car : {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService
                        .getOneCarById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No car was found with this id :" + id)));
    }

    /**
     * {@code PATCH  /cars/:id} : Partial updates given fields of an existing car, field will ignore if it is null
     *
     * @param id     the id of the carDTO to save.
     * @param carDTO the carDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carDTO,
     * @throws IllegalArgumentException {@code 400 (Bad Request)} if the carId or id are invalid UUID.
     * @throws ResourceNotFoundException   {@code 404 (Not Found)} if the carDTO is not found,
     */
    @PatchMapping("/cars/{id}")
    public ResponseEntity<CarDTO> partialUpdateCar(
            @PathVariable("id") String id,
            @NotNull @RequestBody CarDTO carDTO) {
        log.debug("REST request to partial update Car partially : {}, {}", id, carDTO);

        if (!id.equals(carDTO.getCarId())) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService
                        .partialUpdate(carDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("No car was found with this id :" + id)));

    }

    /**
     * {@code POST /cars/damaged} : saves all damaged cars
     *
     * @param carIds  ids list of cars damaged cars
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @PostMapping("/admin/cars/damaged")
    @isAdmin
    public ResponseEntity<Map<String, String>> saveDamagedCars(@RequestBody List<String> carIds) {
        log.debug("REST request to save all damaged cars : {}", carIds);
        String message = carIds.size() <= 1
                ? "No car was found with this id : " + String.join(",", carIds)
                : "No cars was found with these ids : " + String.join(",", carIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", carService.saveDamagedCars(carIds)
                        .orElseThrow(() -> new ResourceNotFoundException(message))));
    }

}
