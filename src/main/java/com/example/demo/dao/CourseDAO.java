package com.example.demo.dao;

import com.example.demo.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDAO extends JpaRepository<Course, Integer> {
}
