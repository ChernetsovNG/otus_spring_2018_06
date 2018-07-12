package ru.nchernetsov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.TestApplicationRunner;
import ru.nchernetsov.service.config.ApplicationSettingsLoader;
import ru.nchernetsov.service.config.LocaleSettingsLoader;
import ru.nchernetsov.service.config.TestsSettingsLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(TestApplicationRunner.class)
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
