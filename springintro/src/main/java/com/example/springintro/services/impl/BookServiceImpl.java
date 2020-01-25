package com.example.springintro.services.impl;

import com.example.springintro.entities.*;
import com.example.springintro.reposotories.AuthorRepository;
import com.example.springintro.reposotories.BookRepository;
import com.example.springintro.reposotories.CategoryRepository;
import com.example.springintro.services.BookService;
import com.example.springintro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOK_PATH = "D:\\Spring\\springintro\\Files\\books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {

        if(this.bookRepository.count() != 0){
            return;
        }

        String[] books = this.fileUtil.fileContent(BOOK_PATH);

        for (String s: books) {

            String[] params = s.split("\\s+");

            Book book = new Book();
            book.setAuthor(randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            book.setCopies(Integer.parseInt(params[2]));

            BigDecimal price = new BigDecimal(params[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();

            for (int i = 5; i <= params.length - 1; i++) {

                title.append(params[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            book.setCategories(randomCategories());

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> findAllTitles() {

        LocalDate releaseDate = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate)
                .stream()
                .map(b -> b.getTitle())
                .collect(Collectors.toList());

    }

    @Override
    public List<String> findAllAuthors() {
        LocalDate releaseDate = LocalDate.parse("1/1/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(b -> String.format("%s %s", b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthor() {

        return this.bookRepository.findAllByAuthor_Id(getAuthorId())
                .stream()
                .sorted((Comparator.comparing(Book::getReleaseDate)).reversed()
                        .thenComparing((Comparator.comparing(Book::getReleaseDate)).reversed()))
                .map(book -> String.format("%s %s %s", book.getTitle(),
                        book.getReleaseDate(), book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> countAuthorBooks() {

        return null;
    }


    private int getAuthorId(){
        return this.authorRepository.findAuthorByFirstNameAndLastName("George", "Powell")
                .getId();
    }



    private Author randomAuthor(){

        Random random = new Random();

       int index = random.nextInt((int) this.authorRepository.count()) + 1;

        return this.authorRepository.getOne(index);
    }

    private Category randomCategory(){

        Random random = new Random();

        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        return this.categoryRepository.getOne(index);
    }

    private Set<Category> randomCategories(){

        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }

        return categories;
    }
}
