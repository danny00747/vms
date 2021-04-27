package be.rentvehicle.service.dto;

import be.rentvehicle.domain.PricingClass;
import be.rentvehicle.domain.enumeration.PRICINGCLASS;
import lombok.Data;

/**
 * A DTO for the {@link PricingClass} entity.
 */
public @Data class PricingClassDTO {

    private PRICINGCLASS className;

    private Integer dailyFine;

    private Integer priceByKm;

    private Integer allowedKmPerDay;

    private Integer costPerDay;

}
