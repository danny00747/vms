package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Booking;
import be.rentvehicle.domain.enumeration.BOOKINGSTATE;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

/**
 * A DTO for the {@link Booking} entity.
 */
public @Data class BookingDTO {

    private String bookingId;

    private Instant cancellationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BOOKINGSTATE bookingState;

    private Instant withdrawalDate;

    private Instant returnDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CarDTO carDTO;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RentDTO rentDTO;
}
