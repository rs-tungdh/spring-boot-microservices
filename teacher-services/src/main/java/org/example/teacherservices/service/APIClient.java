package org.example.teacherservices.service;

import org.example.teacherservices.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080", value = "STUDENT-SERVICE")
public interface APIClient {
    @GetMapping("students/{id}")
    StudentDto retrieveStudentDtoById(@PathVariable int id);
}
