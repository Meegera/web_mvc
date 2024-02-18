package org.web_mvc.controller;

import org.springframework.web.bind.annotation.*;
import org.web_mvc.model.Post;
import org.web_mvc.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all(){
        return service.all();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public Post save(Post post){
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id){
        service.removeById(id);
    }
}
