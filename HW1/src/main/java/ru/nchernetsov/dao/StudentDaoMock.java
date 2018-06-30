package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Student;

import java.util.Optional;

public class StudentDaoMock implements StudentDao {
    /**
     * Имитируем получение студента из базы данных
     *
     * @param firstName - имя
     * @param lastName  - фамилия
     * @return - студент, полученный из базы по имени и фамилии
     */
    @Override
    public Optional<Student> findByName(String firstName, String lastName) {
        return Optional.of(new Student(firstName, lastName));
    }
}
