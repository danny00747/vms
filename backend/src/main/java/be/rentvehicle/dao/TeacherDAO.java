package be.rentvehicle.dao;

import be.rentvehicle.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Teacher} entity.
 */
@Repository
public interface TeacherDAO extends JpaRepository<Teacher, Integer> {

    @Query("select teachers from Teacher teachers")
    List<Teacher> findAllWithEagerRelationships();
}
