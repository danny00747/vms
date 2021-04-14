package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
public @Data class CarsDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String licensePlate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modelType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modelBrand;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int bagsNumber;


}
