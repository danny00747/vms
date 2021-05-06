package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Town;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link Town} entity.
 */
public @Data class TownDTO {

    @NotNull(message = "postcode is a required field.")
    @Min(value = 1, message = "postcode must be greater than or equal to 1")
    private Integer postcode;

    @NotNull(message = "A town's name is a required field.")
    private String name;
}
