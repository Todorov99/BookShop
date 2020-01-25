package com.example.springintro.reposotories;

import com.example.springintro.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);

}
