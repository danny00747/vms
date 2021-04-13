package be.rentvehicle.web.rest;

import be.rentvehicle.service.ModelService;
import be.rentvehicle.service.dto.ModelDTO;
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
public class ModelResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(ModelResource.class);

    private final ModelService modelService;

    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * {@code GET  /models} : get all the cars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/models")
    public ResponseEntity<List<ModelDTO>> getAllModels() {

        log.debug("REST request to get a list of Models");
        List<ModelDTO> models = modelService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }
}
