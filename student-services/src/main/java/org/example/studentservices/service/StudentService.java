package org.example.studentservices.service;

import org.example.studentservices.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class StudentService {
    private static List<Student> students = new ArrayList<>();

    private static int studentCount = 0;

    static {
        students.add(new Student(++studentCount, "A", "01/01/2000", "HN"));
        students.add(new Student(++studentCount, "B", "02/01/2000", "ĐN"));
        students.add(new Student(++studentCount, "C", "03/01/2000", "HCM"));
    }

    public List<Student> findAllStudent() {
        return students;
    }

    public Student findStudentById(int id) {
        Predicate<? super Student> predicate = student -> student.getId() == id;

        return students.stream().filter(predicate).findFirst().orElse(null);
    }

    public Student saveStudent(Student student) {
        student.setId(++studentCount);
        students.add(student);
        return student;
    }

    public void deleteStudentById(int id) {
        Predicate<? super Student> predicate = student -> student.getId() == id;
        students.removeIf(predicate);
    }
}
