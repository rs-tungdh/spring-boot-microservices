package org.example.studentservices.service.implement;

import org.example.studentservices.dto.StudentDto;
import org.example.studentservices.model.Student;
import org.example.studentservices.repository.StudentRepository;
import org.example.studentservices.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(int studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        // convert student dto to student jpa entity
        Student student = new Student(
                studentDto.getId(),
                studentDto.getName(),
                studentDto.getDateOfBirth(),
                studentDto.getAddress()
        );

        Student savedStudent = studentRepository.save(student);

        return new StudentDto(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getDateOfBirth(),
                savedStudent.getAddress()
        );
    }

    @Override
    public void deleteById(int studentId) {
        studentRepository.deleteById(studentId);
    }
}
