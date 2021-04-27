package be.rentvehicle.domain;

import javax.persistence.*;

import be.rentvehicle.domain.enumeration.PRICINGCLASS;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link PricingClass} entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pricing_class")
public @Data class PricingClass extends AbstractAuditingEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "class_name")
    private PRICINGCLASS className;

    @Column(name = "daily_fine")
    private Integer dailyFine;

    @Column(name = "price_by_km")
    private Integer priceByKm;

    @Column(name = "allowed_km_per_day")
    private Integer allowedKmPerDay;

    @Column(name = "cost_per_day")
    private Integer costPerDay;

    @OneToMany(mappedBy = "pricingClass")
    private Set<Model> models = new HashSet<>();
}
