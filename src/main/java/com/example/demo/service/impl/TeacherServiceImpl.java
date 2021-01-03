package com.example.demo.service.impl;

import com.example.demo.dao.TeacherDAO;
import com.example.demo.domain.Teacher;
import com.example.demo.service.TeacherService;
import com.example.demo.service.dto.TeacherDTO;
import com.example.demo.service.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDAO teacherDAO;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherDAO teacherDAO, TeacherMapper teacherMapper) {
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
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
