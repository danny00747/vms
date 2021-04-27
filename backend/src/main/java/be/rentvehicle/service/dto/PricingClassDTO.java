package be.rentvehicle.service.dto;

import lombok.Data;


public @Data class PricingClassDTO {

    private String className;

    private Integer dailyFine;

    private Integer priceByKm;

    private Integer allowedKmPerDay;

    private Integer costPerDay;

}
