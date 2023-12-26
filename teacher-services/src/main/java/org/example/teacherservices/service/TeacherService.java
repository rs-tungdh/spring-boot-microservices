package org.example.teacherservices.service;

import org.example.teacherservices.dto.APIResponseDto;
import org.example.teacherservices.dto.TeacherDto;
import org.example.teacherservices.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    public List<Teacher> findAll();

    public APIResponseDto findById(int teacherId);

    public TeacherDto save(TeacherDto teacherDto);
}
