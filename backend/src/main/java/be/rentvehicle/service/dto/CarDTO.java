package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Car;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A DTO for the {@link Car} entity.
 */

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
