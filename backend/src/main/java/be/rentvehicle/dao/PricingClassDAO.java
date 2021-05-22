package be.rentvehicle.dao;

import be.rentvehicle.domain.PricingClass;
import be.rentvehicle.domain.enumeration.PRICINGCLASS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link PricingClass} entity.
 */
@Repository
public interface PricingClassDAO extends JpaRepository<PricingClass, PRICINGCLASS> {
}
