package com.example.springintro.controllers;

import com.example.springintro.services.AuthorService;
import com.example.springintro.services.BookService;
import com.example.springintro.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public AppController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();

        //findAllBookTitles();
        //findAllAuthors();

        findAllBookByAuthor();

    }

    private void findAllBookByAuthor() {
        List<String> bookAuthors = this.bookService.findAllBooksByAuthor();
        bookAuthors.forEach(System.out::println);
    }

    private void findAllAuthors() {
        List<String> authors = this.bookService.findAllAuthors();
        authors.forEach(System.out::println);
    }

    private void findAllBookTitles() {
        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);
    }
}
