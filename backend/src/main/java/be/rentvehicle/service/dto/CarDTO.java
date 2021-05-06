package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public @Data
class CarDTO {


    private String carId;

    private Integer madeInYear;

    private Boolean isDamaged = false;

    private Integer purchasedPrice;

    private String licensePlate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ModelDTO modelDTO;
}
