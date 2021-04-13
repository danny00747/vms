package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

public @Data class ModelDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID modelId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modelType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String brand;

}
