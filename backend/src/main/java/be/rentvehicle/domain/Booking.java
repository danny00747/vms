package be.rentvehicle.domain;

import be.rentvehicle.domain.enumeration.BOOKINGSTATE;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * The Booking entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "booking")
public @Data class Booking extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "id")
    private UUID id;

    @Column(name = "cancellation_date")
    private Instant cancellationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_state")
    private BOOKINGSTATE bookingState;

    @Column(name = "withdrawal_date")
    private Instant withdrawalDate;

    @Column(name = "return_date")
    private Instant returnDate;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "car_id", unique = true)
    private Car car;

    @OneToOne(mappedBy = "booking")
    private Rent rent;
}
