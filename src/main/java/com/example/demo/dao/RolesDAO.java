package com.example.demo.dao;

import com.example.demo.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Spring Data JPA repository for the {@link Roles} entity.
 */
public interface RolesDAO extends JpaRepository<Roles, String> {
}
