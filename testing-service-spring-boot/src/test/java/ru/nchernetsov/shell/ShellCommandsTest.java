package ru.nchernetsov.shell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.TestApplicationRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(TestApplicationRunner.class)
public class ShellCommandsTest {

    @Autowired
    private Shell shell;

    @Test
    public void addCommandTest() {
        Object result = shell.evaluate(() -> "add 1 3");
        assertEquals(4, result);
    }

    @Test
    public void greetCommandTest1() {
        Object result = shell.evaluate(() -> "greet");
        assertEquals("Hello World", result);
    }

    @Test
    public void greetCommandTest2() {
        Object result = shell.evaluate(() -> "greet Nikita");
        assertEquals("Hello Nikita", result);
    }
}
