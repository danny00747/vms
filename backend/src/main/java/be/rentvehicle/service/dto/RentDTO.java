package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Rent;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * A DTO for the {@link Rent} entity.
 */
public @Data class RentDTO {

    private String rentId;

    @NotNull(message = "licenseNumber is a required field.")
    @Size(min = 10, max = 10, message = "A licenseNumber length must be 10.")
    private String licenseNumber;

    @NotNull(message = "withdrawalKm is a required field.")
    private Integer withdrawalKm;

    @NotNull(message = "returnKm is a required field.")
    private Integer returnKm;

    @NotNull(message = "effectiveReturnDate is a required field.")
    private Instant effectiveReturnDate;

    @NotNull(message = "cautionPayment is a required field.")
    private boolean cautionPayment;
}
