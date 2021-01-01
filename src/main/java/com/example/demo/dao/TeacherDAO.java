package com.example.demo.dao;

import com.example.demo.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherDAO extends JpaRepository<Teacher, Integer> {

    @Query("select discipline from Teacher discipline")
    List<Teacher> findAllWithEagerRelationships();
}
