package com.example.blog.controller;

import com.example.blog.model.Category;
import com.example.blog.model.Post;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/")
    public String main(ModelMap map){
        List<Post> all = postRepository.findAll();
        List<Category> allCategories = categoryRepository.findAll();
        map.addAttribute("posts",all);
        map.addAttribute("categories",allCategories);
        return "index";
    }

}

