package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @GetMapping("/add")
    public String addPostView(ModelMap map) {
        map.addAttribute("categories",categoryRepository.findAll());
        return "addPost";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute Post post, @RequestParam("picture") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imageUploadDir + File.separator + fileName);
        file.transferTo(picture);
        post.setPicUrl(fileName);
        post.setCreatedDate(new Date());
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") int id) {
        Optional<Post> one = postRepository.findById(id);
        if (one.isPresent()) {
            postRepository.deleteById(id);
        }
        return "redirect:/";
    }
    @GetMapping("/postById")
    public String postById(@RequestParam("id") int id, ModelMap map) {
        map.addAttribute("post", postRepository.getOne(id));
        return "post";
    }
    @GetMapping("/postByCategoryId")
    public String postByCategoryId(@RequestParam("id") int id, ModelMap map) {
        List<Post> all = postRepository.findAll();
        List<Post> postByCategoryId = new LinkedList<>();
        for (Post post : all) {
            if (post.getCategory().getId() == id)  {
                postByCategoryId.add(post);
            }
        }
        map.addAttribute("posts", postByCategoryId);
        return "postByCategory";
    }

    @GetMapping("/getImage")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }
}
