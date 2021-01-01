package com.example.demo.controllers;

import com.example.demo.service.TeacherService;
import com.example.demo.service.dto.TeacherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    private final Logger log = LoggerFactory.getLogger(TeacherController.class);

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * {@code POST  /create} : Create a new teacher.
     *
     * @param teacherDTO the teacherDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teacherDTO
     */
    @PostMapping("/create")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        log.debug("REST request to create a Teacher");
        TeacherDTO createdTeacher = this.teacherService.save(teacherDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdTeacher);
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDTO>> getTeachers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Teachers");
        log.info("REST request to get all Teachers");
        List<TeacherDTO> teachers = teacherService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(teachers);
    }
}
