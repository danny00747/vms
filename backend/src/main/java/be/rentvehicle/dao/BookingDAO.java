package be.rentvehicle.dao;

import be.rentvehicle.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data  repository for the Booking entity.
 */
@Repository
public interface BookingDAO extends JpaRepository<Booking, UUID> {
}
