package com.nhnacademy.jdbc.board.student.service.impl;

import com.nhnacademy.jdbc.board.student.domain.Student;
import com.nhnacademy.jdbc.board.student.mapper.StudentMapper;
import com.nhnacademy.jdbc.board.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 17/05/2022
 */

@Service
public class DefaultStudentService implements StudentService {
    private final StudentMapper studentMapper;

    public DefaultStudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Optional<Student> getStudent(long id){
        return studentMapper.selectStudent(id);
    }
}
