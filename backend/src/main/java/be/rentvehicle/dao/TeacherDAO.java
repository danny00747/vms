package be.rentvehicle.dao;

import be.rentvehicle.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Spring Data JPA repository for the {@link Teacher} entity.
 */
public interface TeacherDAO extends JpaRepository<Teacher, Integer> {

    @Query("select teachers from Teacher teachers")
    List<Teacher> findAllWithEagerRelationships();
}
