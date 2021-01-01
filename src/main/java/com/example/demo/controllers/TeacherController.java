package com.example.demo.controllers;

import com.example.demo.dao.TeacherDAO;
import com.example.demo.domain.Teacher;
import com.example.demo.service.TeacherService;
import com.example.demo.service.dto.TeacherDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacher) {
        TeacherDTO teacherCreated = this.teacherService.save(teacher);
        return ResponseEntity.status(HttpStatus.OK).body(teacherCreated);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TeacherDTO>> getTeachers(@RequestParam(required = false, defaultValue = "false") boolean eagerlo) {
        List<TeacherDTO> teachers = teacherService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(teachers);
    }

}
