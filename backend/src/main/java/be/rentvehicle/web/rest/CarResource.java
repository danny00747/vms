package be.rentvehicle.web.rest;

import be.rentvehicle.service.CarService;
import be.rentvehicle.service.dto.CarDTO;
import be.rentvehicle.service.impl.errors.CarNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @throws CarNotFoundException {@code 404 (Not Found)} if the car couldn't be returned.
     */
    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") String id) {
        log.debug("REST request to get car : {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.getOneCarById(id)
                        .orElseThrow(() -> new CarNotFoundException(id)));
    }

}
