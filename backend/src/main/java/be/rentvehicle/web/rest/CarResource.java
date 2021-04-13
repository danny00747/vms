package be.rentvehicle.web.rest;

import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

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
    public ResponseEntity<List<CarDTO>> getAllCars() {

        log.debug("REST request to get a list of Cars");
        List<CarDTO> cars = carService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }
}
