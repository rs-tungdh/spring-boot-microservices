package org.example.teacherservices.service.implement;

import org.example.teacherservices.dto.APIResponseDto;
import org.example.teacherservices.dto.StudentDto;
import org.example.teacherservices.dto.TeacherDto;
import org.example.teacherservices.model.Teacher;
import org.example.teacherservices.repository.TeacherRepository;
import org.example.teacherservices.service.APIClient;
import org.example.teacherservices.service.TeacherService;
import org.example.teacherservices.service.exception.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private APIClient apiClient;
//    private RestTemplate restTemplate;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public APIResponseDto findById(int teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);

        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("id" + teacherId);
        }

//        ResponseEntity<StudentDto> responseEntity = restTemplate.getForEntity(
//                "http://localhost:8080/students/" + teacher.get().getStudentId(),
//                StudentDto.class
//        );

        StudentDto studentDto = apiClient.retrieveStudentDtoById(teacher.get().getStudentId());

        TeacherDto teacherDto = new TeacherDto(
                teacher.get().getId(),
                teacher.get().getName(),
                teacher.get().getDateOfBirth(),
                teacher.get().getAddress(),
                teacher.get().getSubject(),
                teacher.get().getStudentId()
        );

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setStudentDto(studentDto);
        apiResponseDto.setTeacherDto(teacherDto);
        return apiResponseDto;
    }

    @Override
    public TeacherDto save(TeacherDto teacherDto) {
        Teacher teacher = new Teacher(
                teacherDto.getId(),
                teacherDto.getName(),
                teacherDto.getDateOfBirth(),
                teacherDto.getAddress(),
                teacherDto.getSubject(),
                teacherDto.getStudentId()
        );

        Teacher savedTeacher = teacherRepository.save(teacher);

        return new TeacherDto(
                savedTeacher.getId(),
                savedTeacher.getName(),
                savedTeacher.getDateOfBirth(),
                savedTeacher.getAddress(),
                savedTeacher.getSubject(),
                savedTeacher.getStudentId()
        );
    }
}
