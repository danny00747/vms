package be.rentvehicle.service;

import be.rentvehicle.domain.Teacher;
import be.rentvehicle.service.dto.TeacherDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * TeacherService interface for the {@link Teacher} entity.
 */
public interface TeacherService {

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    TeacherDTO save(@Valid TeacherDTO teacherDTO);

    /**
     * Get all the teachers.
     *
     * @return the list of entities of type Teacher.
     */
    List<TeacherDTO> findAll();
}
