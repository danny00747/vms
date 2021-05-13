package be.rentvehicle.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * The Rent entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rent")
public @Data
class Rent extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "id")
    private UUID id;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "withdrawal_km", nullable = false)
    private Integer withdrawalKm;

    @Column(name = "return_km", nullable = false)
    private Integer returnKm;

    @Column(name = "effective_return_date", nullable = false)
    private Instant effectiveReturnDate;

    @Column(name = "caution_payment", nullable = false)
    private boolean cautionPayment;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}

