package com.example.demo.service;

import com.example.demo.service.dto.TeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface TeacherService {

    /**
     *
     * @param teacher is a teacher
     */
    TeacherDTO save(@NotNull TeacherDTO teacher);
    List<TeacherDTO> findAll();
}
