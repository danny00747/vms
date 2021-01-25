package be.rentvehicle.service.impl;

import be.rentvehicle.dao.TeacherDAO;
import be.rentvehicle.domain.Teacher;
import be.rentvehicle.service.TeacherService;
import be.rentvehicle.service.dto.TeacherDTO;
import be.rentvehicle.service.mapper.TeacherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the {@link TeacherService} interface.
 */
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
        return teacherDAO.findAllWithEagerRelationships()
                .stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }
}
