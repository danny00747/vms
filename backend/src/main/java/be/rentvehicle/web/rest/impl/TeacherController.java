package be.rentvehicle.web.rest.impl;

import be.rentvehicle.service.TeacherService;
import be.rentvehicle.service.dto.TeacherDTO;
import be.rentvehicle.web.rest.TeacherResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Component
public class TeacherController extends BaseRestController implements TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherController.class);

    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        log.debug("REST request to create a Teacher");
        TeacherDTO createdTeacher = this.teacherService.save(teacherDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @Override
    public ResponseEntity<List<TeacherDTO>> getTeachers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Teachers");
        log.info("REST request to get all Teachers");
        List<TeacherDTO> teachers = teacherService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(teachers);
    }
}

   /*
        var t = new ProductDto.Request.Create("ss", 0.2, 0.3);
        System.out.println(t);
        var s = ProductDto.getMarkup(t);
        System.out.println(s);
         */
