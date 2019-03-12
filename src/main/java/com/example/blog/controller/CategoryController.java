package com.example.blog.controller;

import com.example.blog.model.Category;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/add")
    public String addCategoryView() {
        return "addCategory";
    }

    @PostMapping("/add")
    public String addCategory(ModelMap map, @ModelAttribute Category category) throws IOException {

        categoryRepository.save(category);
        map.addAttribute("posts", postRepository.findAll());
        map.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/";
    }

}
