package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.student.domain.Student;
import com.nhnacademy.jdbc.board.student.service.StudentService;
import com.nhnacademy.jdbc.board.student.service.impl.DefaultStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping
@Slf4j
public class IndexController {
    private final StudentService studentService;

    public IndexController(DefaultStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = {"/","/index.nhn"})
    public String index(){
        Optional<Student> student = studentService.getStudent(1l);
        if(student.isPresent()){
            log.debug("student : {}",student.get());
        }
        return "index/index";
    }
}
