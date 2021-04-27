
package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public @Data class ModelDTO {

    private String modelId;

    private String modelType;

    private String brand;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ModelOptionDTO modelOptionDTO;

    private PricingClassDTO princingDetailsDTO;

}
