package org.example.studentservices.service;

import org.example.studentservices.dto.StudentDto;
import org.example.studentservices.model.Student;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    public List<Student> findAll();

    public Optional<Student> findById(int studentId);

    public StudentDto save(StudentDto studentDto);

    public void deleteById(int studentId);
}
