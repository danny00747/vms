package be.rentvehicle.service.dto;

import be.rentvehicle.domain.PricingClass;
import be.rentvehicle.domain.enumeration.PRICINGCLASS;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A DTO for the {@link PricingClass} entity.
 */
public @Data class PricingClassDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PRICINGCLASS className;

    private Integer dailyFine;

    private Integer priceByKm;

    private Integer allowedKmPerDay;

    private Integer costPerDay;

}
