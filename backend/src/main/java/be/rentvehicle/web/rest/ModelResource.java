package be.rentvehicle.web.rest;

import be.rentvehicle.service.ModelService;
import be.rentvehicle.service.dto.ModelDTO;
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
public class ModelResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(ModelResource.class);

    private final ModelService modelService;

    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * {@code GET  /models} : get all the cars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of models in body.
     */
    @GetMapping("/models")
    public ResponseEntity<List<ModelDTO>> getAllModels() {

        log.debug("REST request to get a list of Models");
        List<ModelDTO> models = modelService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }

    /**
     * {@code PATCH  /cars/:id} : Partial updates given fields of an existing car, field will ignore if it is null
     *
     * @param id the id of the modelDTO to save.
     * @param modelDTO the modelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelDTO,
     * @throws IllegalArgumentException {@code 400 (Bad Request)} if the modelId or id are invalid UUID.
     * @throws ResourceNotFoundException {@code 404 (Not Found)} if the carDTO is not found,
     */
    @PatchMapping("/models/{id}")
    public ResponseEntity<ModelDTO> partialUpdateCar(
            @PathVariable("id") String id,
            @NotNull @RequestBody ModelDTO modelDTO)  {
        log.debug("REST request to partial update Model partially : {}, {}", id, modelDTO);

        if (!id.equals(modelDTO.getModelId())) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(modelService
                        .partialUpdate(modelDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("No model was found with this id :" + id)));

    }
}
