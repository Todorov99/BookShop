package com.example.springintro.services.impl;

import com.example.springintro.entities.Category;
import com.example.springintro.reposotories.CategoryRepository;
import com.example.springintro.services.CategoryService;
import com.example.springintro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final String CATEGORY_FILE_PATH = "D:\\Spring\\springintro\\Files\\categories.txt";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {

        if (this.categoryRepository.count() != 0){
            return;
        }

        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);

        for (String s: categories) {

            Category category = new Category();
            category.setName(s);

            this.categoryRepository.saveAndFlush(category);
        }
    }
}
