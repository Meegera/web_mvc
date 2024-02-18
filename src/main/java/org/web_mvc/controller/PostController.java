package org.web_mvc.controller;

import org.springframework.web.bind.annotation.*;
import org.web_mvc.model.Post;
import org.web_mvc.model.PostDTO;
import org.web_mvc.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<PostDTO> all(){
        return service.all();
    }

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public PostDTO save(Post post){
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id){
        service.removeById(id);
    }
}
