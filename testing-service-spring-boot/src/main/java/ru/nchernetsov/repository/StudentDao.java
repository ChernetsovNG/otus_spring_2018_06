package ru.nchernetsov.repository;

import ru.nchernetsov.domain.Student;

public interface StudentDao {

    Student findByName(String firstName, String lastName);
}
