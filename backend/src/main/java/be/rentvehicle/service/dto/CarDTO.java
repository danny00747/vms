package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.UUID;

public @Data class CarDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID carId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer madeInYear;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isDamaged = false;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer purchasedPrice;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String licensePlate;

    private String modelBrand;
    private String modelType;

}
