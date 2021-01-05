package com.example.demo.service.mapper;
import com.example.demo.config.TeacherDto;
import com.example.demo.service.dto.TeacherDTO;

import java.util.List;



/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public sealed interface EntityMapper <D, E> permits TeacherMapper {

    E toEntity(D dto);

    D toDto(E entity);

    List <E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);
}


