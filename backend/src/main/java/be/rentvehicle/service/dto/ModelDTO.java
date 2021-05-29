
package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A DTO for the {@link Model} entity.
 */
public @Data class ModelDTO {

    private String modelId;

    private String modelType;

    private String brand;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ModelOptionDTO modelOptionDTO;

    private PricingClassDTO princingDetailsDTO;

}
