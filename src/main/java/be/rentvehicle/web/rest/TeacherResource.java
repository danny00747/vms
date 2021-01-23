package be.rentvehicle.web.rest;

import be.rentvehicle.service.dto.TeacherDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public interface TeacherResource {

    /**
     * {@code POST  /create} : Create a new teacher.
     *
     * @param teacherDTO the teacherDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teacherDTO
     */
    @PostMapping("/create")
    ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO);

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    ResponseEntity<List<TeacherDTO>> getTeachers(@RequestParam(required = false, defaultValue = "false") boolean eagerload);
}
