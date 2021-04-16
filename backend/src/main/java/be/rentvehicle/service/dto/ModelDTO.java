
package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public @Data class ModelDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modelId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modelType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String brand;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ModelOptionDTO modelOptionDTO;

}
