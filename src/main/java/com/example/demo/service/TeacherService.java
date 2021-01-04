package com.example.demo.service;

import com.example.demo.config.TeacherDto;
import com.example.demo.service.dto.TeacherDTO;

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
     * @return the list of entities.
     */
    List<TeacherDTO> findAll();
}
