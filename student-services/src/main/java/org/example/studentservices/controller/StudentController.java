package org.example.studentservices.controller;

import jakarta.validation.Valid;
import org.example.studentservices.dto.StudentDto;
import org.example.studentservices.model.Point;
import org.example.studentservices.model.Student;
import org.example.studentservices.repository.PointRepository;
import org.example.studentservices.service.exception.StudentNotFoundException;
import org.example.studentservices.service.implement.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Autowired
    private PointRepository pointRepository;

    @GetMapping("/students")
    public List<Student> retrieveAllUsers() {
        return studentServiceImpl.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDto> retrieveStudentById(@PathVariable int id) {
        Optional<Student> student = studentServiceImpl.findById(id);

        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }

//        EntityModel<Student> entityModel = EntityModel.of(student.get());
//
//        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        entityModel.add(link.withRel("all-users"));
//
//        return entityModel;

        StudentDto studentDto = new StudentDto(
                student.get().getId(),
                student.get().getName(),
                student.get().getDateOfBirth(),
                student.get().getAddress()
        );

        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto savedStudent = studentServiceImpl.save(studentDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentServiceImpl.deleteById(id);
    }

    @GetMapping("/students/{id}/points")
    public List<Point> retrievePointsForStudent(@PathVariable int id) {
        Optional<Student> studentDto = studentServiceImpl.findById(id);

        if (studentDto.isEmpty()) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }

        return studentDto.get().getPoints();
    }

    @PostMapping("students/{id}/points")
    public ResponseEntity<Point> setPointForStudent(@PathVariable int id, @Valid @RequestBody Point point) {
        Optional<Student> student = studentServiceImpl.findById(id);

        if (student.isEmpty()) {
            throw new StudentNotFoundException("id" + id);
        }

        point.setStudent(student.get());

        Point savedPoint = pointRepository.save(point);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPoint.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
