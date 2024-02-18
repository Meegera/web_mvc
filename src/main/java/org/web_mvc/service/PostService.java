package org.web_mvc.service;

import org.springframework.stereotype.Service;
import org.web_mvc.exception.NotFoundException;
import org.web_mvc.model.Post;
import org.web_mvc.model.PostDTO;
import org.web_mvc.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostDTO> all() {
        return repository.all();
    }

    public PostDTO getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public PostDTO save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}