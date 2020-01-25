package com.example.springintro.reposotories;

import com.example.springintro.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Integer> {

}
