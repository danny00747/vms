package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public @Data class ModelOptionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer bagsNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String isAutomatic;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String hasAirConditioner;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String seatsNumber;
}
