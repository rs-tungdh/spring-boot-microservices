package org.example.studentservices.controller;

import jakarta.validation.Valid;
import org.example.studentservices.model.Point;
import org.example.studentservices.model.Student;
import org.example.studentservices.repository.PointRepository;
import org.example.studentservices.repository.StudentRepository;
import org.example.studentservices.service.exception.StudentNotFoundException;
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
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PointRepository pointRepository;

    @GetMapping("/students")
    public List<Student> retrieveAllUsers() {
//        return studentService.findAllStudent();
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public EntityModel<Student> retrieveStudentById(@PathVariable int id) {
//        Student student = studentService.findStudentById(id);
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentNotFoundException("id" + id);
        }

        EntityModel<Student> entityModel = EntityModel.of(student.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
//        Student savedStudent = studentService.saveStudent(student);
        Student savedStudent = studentRepository.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id) {
//        studentService.deleteStudentById(id);
        studentRepository.deleteById(id);
    }

    @GetMapping("/students/{id}/points")
    public List<Point> retrievePointsForStudent(@PathVariable int id) {
//        studentService.deleteStudentById(id);
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentNotFoundException("id" + id);
        }

        return student.get().getPoints();
    }

    @PostMapping("students/{id}/points")
    public ResponseEntity<Point> setPointForStudent(@PathVariable int id, @Valid @RequestBody Point point) {
        Optional<Student> student = studentRepository.findById(id);

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
