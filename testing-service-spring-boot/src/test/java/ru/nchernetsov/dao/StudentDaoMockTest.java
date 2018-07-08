package ru.nchernetsov.dao;

import org.junit.Test;
import ru.nchernetsov.domain.Student;

import static org.junit.Assert.assertEquals;

public class StudentDaoMockTest {

    private final StudentDao studentDao = new StudentDaoMock();

    @Test
    public void findByNameTest() {
        Student studentByName = studentDao.findByName("Nikita", "Chernetsov");
        assertEquals("Nikita", studentByName.getFirstName());
        assertEquals("Chernetsov", studentByName.getLastName());
    }
}
