package ru.nchernetsov.repository;

import org.junit.jupiter.api.Test;
import ru.nchernetsov.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentDaoMockTest {

    private final StudentDao studentDao = new StudentDaoMock();

    @Test
    void findByNameTest() {
        Student studentByName = studentDao.findByName("Nikita", "Chernetsov");
        assertEquals("Nikita", studentByName.getFirstName());
        assertEquals("Chernetsov", studentByName.getLastName());
    }
}
