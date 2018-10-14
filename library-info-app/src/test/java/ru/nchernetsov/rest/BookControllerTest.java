package ru.nchernetsov.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.nchernetsov.TestData;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.service.AuthorService;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;
import ru.nchernetsov.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.nchernetsov.Utils.getBookIds;
import static ru.nchernetsov.Utils.getBookTitles;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {BookController.class},
    includeFilters = @ComponentScan.Filter(classes = EnableWebSecurity.class))
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    AuthorService authorService;

    @MockBean
    GenreService genreService;

    @MockBean
    CommentService commentService;

    private TestData testData = new TestData();

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void booksListUserTest() throws Exception {
        List<Book> books = new ArrayList<>(testData.getBooksMap().values());
        List<String> bookTitles = getBookTitles(books);

        assertThat(bookTitles).hasSize(6);

        given(bookService.findAll()).willReturn(books);

        this.mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(view().name("books"))
            .andExpect(content().string(containsString(bookTitles.get(0))))
            .andExpect(content().string(containsString(bookTitles.get(1))))
            .andExpect(content().string(containsString(bookTitles.get(2))))
            .andExpect(content().string(containsString(bookTitles.get(3))))
            .andExpect(content().string(containsString(bookTitles.get(4))))
            .andExpect(content().string(containsString(bookTitles.get(5))));
    }

    @Test
    @WithMockUser
    public void deleteBookUserTest() throws Exception {
        List<Book> books = new ArrayList<>(testData.getBooksMap().values());
        List<String> bookIds = getBookIds(books);

        assertThat(bookIds).hasSize(6);

        this.mockMvc.perform(get("/books/delete/" + bookIds.get(0)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteBookAdminTest() throws Exception {
        List<Book> books = new ArrayList<>(testData.getBooksMap().values());
        List<String> bookIds = getBookIds(books);

        assertThat(bookIds).hasSize(6);

        books.remove(books.get(0));
        List<Book> booksAfterDelete = new ArrayList<>(books);

        given(bookService.findAll()).willReturn(booksAfterDelete);

        this.mockMvc.perform(get("/books/delete/" + bookIds.get(0)))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/books"));
    }

}
