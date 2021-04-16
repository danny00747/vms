package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public @Data class ModelOptionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String optionCode;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer bagsNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isAutomatic;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean hasAirConditioner;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer seatsNumber;

}
