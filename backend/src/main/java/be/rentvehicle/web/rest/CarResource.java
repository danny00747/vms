package be.rentvehicle.web.rest;

import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.impl.errors.ResourceFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@Validated
public class CarResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(CarResource.class);

    private final CarService carService;

    public CarResource(CarService carService) {
        this.carService = carService;
    }

    /**
     * {@code GET  /cars} : get all the cars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> getAllCars(@RequestParam(required = false) String brand) {

        if (brand != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(carService.getAllByModelBrand(brand));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(carService.findAll());
        }
    }

    /*
     * {@code GET  /cars} : get all the cars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/carss")
    public ResponseEntity<List<CarDTO>> getAllCarss() {

        log.debug("REST request to get a list of Cars");
        List<CarDTO> cars = carService.finds();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    /**
     * {@code GET /cars/:id} : get the "id" car.
     *
     * @param id the id of the car to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the found car or with status {@code 404 (Not Found)}.
     * @throws IllegalArgumentException {@code 400 (Bad Request)} if the id is an invalid UUID.
     * @throws ResourceFoundException {@code 404 (Not Found)} if the car couldn't be returned.
     */
    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") String id) {
        log.debug("REST request to get car : {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService
                        .getOneCarById(id)
                        .orElseThrow(() -> new ResourceFoundException("No car was found with this id :" + id)));
    }

    /**
     * {@code PATCH  /cars/:id} : Partial updates given fields of an existing car, field will ignore if it is null
     *
     * @param id the id of the carDTO to save.
     * @param carDTO the carDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carDTO,
     * @throws IllegalArgumentException {@code 400 (Bad Request)} if the carId or id are invalid UUID.
     * @throws ResourceFoundException {@code 404 (Not Found)} if the carDTO is not found,
     */
    @PatchMapping("/cars/{id}")
    public ResponseEntity<CarDTO> partialUpdateCar(
            @PathVariable("id") String id,
            @NotNull @RequestBody CarDTO carDTO)  {
        log.debug("REST request to partial update Car partially : {}, {}", id, carDTO);

        if (!id.equals(carDTO.getCarId())) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService
                        .partialUpdate(carDTO)
                        .orElseThrow(() -> new ResourceFoundException("No car was found with this id :" + id)));

    }

}
