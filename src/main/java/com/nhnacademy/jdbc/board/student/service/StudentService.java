package com.nhnacademy.jdbc.board.student.service;

import com.nhnacademy.jdbc.board.student.domain.Student;

import java.util.Optional;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 17/05/2022
 */
public interface StudentService {
     Optional<Student> getStudent(long id);
}
