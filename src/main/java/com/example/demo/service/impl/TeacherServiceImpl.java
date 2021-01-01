package com.example.demo.service.impl;

import com.example.demo.dao.TeacherDAO;
import com.example.demo.domain.Teacher;
import com.example.demo.service.TeacherService;
import com.example.demo.service.dto.TeacherDTO;
import com.example.demo.service.mapper.TeacherMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDAO teacherDAO;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherDAO teacherDAO, TeacherMapper teacherMapper) {
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDTO save(@NotNull TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherDAO.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherDTO> findAll() {
        return teacherDAO.findAllWithEagerRelationships().stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }
}
