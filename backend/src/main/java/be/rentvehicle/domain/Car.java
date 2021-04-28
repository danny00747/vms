package be.rentvehicle.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
public @Data
class Car extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "id")
    private UUID id;

    @Min(value = 1)
    @Column(name = "purchased_price")
    private Integer purchasedPrice;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    @Column(name = "made_in_year", nullable = false)
    private Integer madeInYear;

    @Column(name = "is_damaged")
    private Boolean isDamaged = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id")
    private Model model;

    @OneToOne(mappedBy = "car")
    private Booking booking;

}
