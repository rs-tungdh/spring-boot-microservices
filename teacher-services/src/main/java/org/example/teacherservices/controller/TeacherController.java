package org.example.teacherservices.controller;

import jakarta.validation.Valid;
import org.example.teacherservices.dto.APIResponseDto;
import org.example.teacherservices.dto.TeacherDto;
import org.example.teacherservices.model.Teacher;
import org.example.teacherservices.service.TeacherService;
import org.example.teacherservices.service.implement.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
//    private TeacherRepository teacherRepository;

    @GetMapping("/teachers")
    public List<Teacher> retrieveAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<APIResponseDto> retrieveTeacherById(@PathVariable int id) {
        APIResponseDto apiResponseDto = teacherService.findById(id);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createStudent(@Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto savedTeacher = teacherService.save(teacherDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTeacher.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
