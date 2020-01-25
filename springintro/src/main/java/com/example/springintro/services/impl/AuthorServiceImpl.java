package com.example.springintro.services.impl;

import com.example.springintro.entities.Author;
import com.example.springintro.reposotories.AuthorRepository;
import com.example.springintro.services.AuthorService;
import com.example.springintro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final static String AUTHOR_FILE_PATH = "D:\\Spring\\springintro\\authors.txt";
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;



    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {

        if (this.authorRepository.count() != 0){
            return;
        }

        String[] authors = this.fileUtil.fileContent(AUTHOR_FILE_PATH);

        for (String s: authors) {
            String[] params = s.split("\\s+");
            Author author = new Author();

            author.setFirstName(params[0]);
            author.setLastName(params[1]);

            this.authorRepository.saveAndFlush(author);
        }

    }


}
