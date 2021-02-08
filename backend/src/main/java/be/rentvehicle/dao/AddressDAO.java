package be.rentvehicle.dao;

import be.rentvehicle.domain.Address;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data  repository for the Address entity.
 */
@Repository
public interface AddressDAO extends JpaRepository<Address, UUID> {
}
