package ru.nchernetsov.repository;

import ru.nchernetsov.domain.Student;

public class StudentDaoMock implements StudentDao {
    /**
     * Имитируем получение студента из базы данных
     *
     * @param firstName - имя
     * @param lastName  - фамилия
     * @return - студент, полученный из базы по имени и фамилии
     */
    @Override
    public Student findByName(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        if (student != null) {
            return student;
        } else {
            throw new IllegalStateException("Student " + firstName + " " + lastName + " not found");
        }
    }
}
