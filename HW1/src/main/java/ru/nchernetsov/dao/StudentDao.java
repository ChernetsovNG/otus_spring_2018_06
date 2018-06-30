package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Student;

import java.util.Optional;

public interface StudentDao {

    Student findByName(String firstName, String lastName);
}
