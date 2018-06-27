package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Student;

import java.util.Optional;

public interface StudentDao {

    Optional<Student> findByName(String firstName, String lastName);
}
