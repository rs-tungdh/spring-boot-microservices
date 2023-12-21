package org.example.teacherservices.controller;

import jakarta.validation.Valid;
import org.example.teacherservices.model.Teacher;
import org.example.teacherservices.repository.TeacherRepository;
import org.example.teacherservices.service.exception.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/teachers")
    public List<Teacher> retrieveAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/teachers/{id}")
    public EntityModel<Teacher> retrieveTeacherById(@PathVariable int id) {
        Optional<Teacher> student = teacherRepository.findById(id);

        if (student.isEmpty()) {
            throw new TeacherNotFoundException("id" + id);
        }

        EntityModel<Teacher> entityModel = EntityModel.of(student.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllTeachers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createStudent(@Valid @RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTeacher.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
