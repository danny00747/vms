package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Address;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link Address} entity.
 */
public @Data class AddressDTO {

    private String addressId;

    @NotNull(message = "road is a required field.")
    private String road;

    @Min(value = 1, message = "postBox must be greater than or equal to 1")
    @NotNull(message = "road is a required field.")
    private Integer postBox;

    @Min(value = 1, message = "houseNumber must be greater than or equal to 1")
    @NotNull(message = "houseNumber is a required field.")
    private Integer houseNumber;

    private TownDTO townDTO;
}
