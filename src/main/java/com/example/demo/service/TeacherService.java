package com.example.demo.service;

import com.example.demo.service.dto.TeacherDTO;
import org.eclipse.collections.api.list.ImmutableList;

import javax.validation.Valid;
import java.util.List;

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
