package ru.nchernetsov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockDataLoaderTest {

    @Autowired
    private MockDataLoader mockDataLoader;

    @Test
    public void mockDataLoaderTest() {
        assertTrue(mockDataLoader.isLoadMockData());
        assertEquals(0.1, mockDataLoader.getVersion(), 1e-6);
    }

}
