package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.repository.AuthorRepository;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public String listPage(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        return "edit";
    }

    @PostMapping("/save")
    public String savePage(@RequestParam("id") String id, @RequestParam("name") String name) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
        author.setName(name);
        authorRepository.save(author);
        return "redirect:/";
    }

}
