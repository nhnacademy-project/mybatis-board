package com.nhnacademy.jdbc.board.student.mapper;

import com.nhnacademy.jdbc.board.student.domain.Student;
import java.util.List;
import java.util.Optional;

public interface StudentMapper {

    Optional<Student> selectStudent(long id);

    List<Student> selectStudents();

    void insertStudent(Student student);

    void updateNameById(String name, long id);

    void deleteById(long id);
}
