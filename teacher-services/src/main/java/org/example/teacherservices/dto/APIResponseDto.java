package org.example.teacherservices.dto;

public class APIResponseDto {
    private StudentDto studentDto;

    private TeacherDto teacherDto;

    public APIResponseDto(StudentDto studentDto, TeacherDto teacherDto) {
        this.studentDto = studentDto;
        this.teacherDto = teacherDto;
    }

    public StudentDto getStudentDto() {
        return studentDto;
    }

    public APIResponseDto() {
    }

    public void setStudentDto(StudentDto studentDto) {
        this.studentDto = studentDto;
    }

    public TeacherDto getTeacherDto() {
        return teacherDto;
    }

    public void setTeacherDto(TeacherDto teacherDto) {
        this.teacherDto = teacherDto;
    }
}
