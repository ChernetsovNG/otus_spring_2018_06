package ru.nchernetsov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.service.config.ApplicationSettingsLoader;
import ru.nchernetsov.service.config.LocaleSettingsLoader;
import ru.nchernetsov.service.config.TestsSettingsLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SettingsLoaderTest {

    @Autowired
    private ApplicationSettingsLoader applicationSettingsLoader;

    @Autowired
    private TestsSettingsLoader testsSettingsLoader;

    @Autowired
    private LocaleSettingsLoader localeSettingsLoader;

    @Test
    public void applicationSettingsLoaderTest() {
        assertTrue(applicationSettingsLoader.isLoadMockData());
        assertEquals(0.1, applicationSettingsLoader.getVersion(), 1e-6);
    }

    @Test
    public void testsSettingsLoaderTest() {
        assertEquals("tests", testsSettingsLoader.getFolder());
        assertEquals(75, testsSettingsLoader.getThreshold());
    }

    @Test
    public void localeSettingsLoaderTest() {
        assertEquals("ru", localeSettingsLoader.getLocale());
    }

}
