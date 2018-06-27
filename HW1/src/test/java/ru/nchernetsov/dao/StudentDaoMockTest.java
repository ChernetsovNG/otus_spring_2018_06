package ru.nchernetsov.dao;

import org.junit.jupiter.api.Test;
import ru.nchernetsov.domain.Student;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoMockTest {

    private StudentDao studentDao = new StudentDaoMock();

    @Test
    void findByNameTest() {
        Optional<Student> studentByName = studentDao.findByName("Nikita", "Chernetsov");
        assertTrue(studentByName.isPresent());
        assertEquals("Nikita", studentByName.get().getFirstName());
        assertEquals("Chernetsov", studentByName.get().getLastName());
    }
}
