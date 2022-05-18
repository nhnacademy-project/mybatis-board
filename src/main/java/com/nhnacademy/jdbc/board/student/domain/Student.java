package com.nhnacademy.jdbc.board.student.domain;

import java.time.LocalDateTime;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 17/05/2022
 */
public class Student {
    private final int id;
    private final String name;
    private final LocalDateTime createdAt;

    public Student(int id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
