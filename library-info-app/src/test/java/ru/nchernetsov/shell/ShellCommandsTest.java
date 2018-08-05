package ru.nchernetsov.shell;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@ActiveProfiles("test")
public class ShellCommandsTest {

    @Autowired
    private Shell shell;

    @Test
    public void findBookByTitleTest() {
        Book book = (Book) shell.evaluate(() ->
                "find-book-by-title --title Оно");

        System.out.println(book.getTitle());
        System.out.println(book.getAuthors());
        System.out.println(book.getGenres());
        System.out.println(book.getComments());
    }

}
