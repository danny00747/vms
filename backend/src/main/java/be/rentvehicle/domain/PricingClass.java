package be.rentvehicle.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * A PricingClass.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pricing_class")
public @Data class PricingClass extends AbstractAuditingEntity {

    @Id
    @Column(name = "class_name")
    private String className;

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
