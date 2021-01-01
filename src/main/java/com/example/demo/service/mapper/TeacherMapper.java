package com.example.demo.service.mapper;

import com.example.demo.domain.Teacher;
import com.example.demo.service.dto.TeacherDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teacher} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

    Teacher toEntity(TeacherDTO teacherDTO);

    default Teacher fromId(int id) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }
}
