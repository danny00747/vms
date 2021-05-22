package be.rentvehicle.web.rest;

import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.service.PricingClassService;
import be.rentvehicle.service.dto.PricingClassDTO;
import be.rentvehicle.service.impl.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@Validated
public class PricingClassResource extends BaseRestController{

    private static final Logger log = LoggerFactory.getLogger(PricingClassResource.class);

    private final PricingClassService pricingClassService;

    public PricingClassResource(PricingClassService pricingClassService) {
        this.pricingClassService = pricingClassService;
    }

    /**
     * {@code GET  /pricingClass} : get all the pricing classes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/pricingClass")
    @isAdmin
    public ResponseEntity<List<PricingClassDTO>> getAllCars() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pricingClassService.getAll());
    }

    /**
     * {@code PATCH  /pricingClass/:className} : Partial updates given fields of an existing pricingClass, field will ignore if it is null
     *
     * @param className the class name of the pricingClass to update.
     * @param pricingClassDTO the pricingClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelDTO,
     * @throws ResourceNotFoundException {@code 404 (Not Found)} if the class name is not found,
     */
    @PatchMapping("/pricingClass/{className}")
    @isAdmin
    public ResponseEntity<PricingClassDTO> partialUpdatePricingClass(
            @PathVariable("className") String className,
            @NotNull @RequestBody PricingClassDTO pricingClassDTO)  {
        log.debug("REST request to partial update PricingClass partially : {}, {}", className, pricingClassDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pricingClassService
                        .updatePricingClass(className, pricingClassDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("No pricing class was found with this class :" + className)));

    }
}
