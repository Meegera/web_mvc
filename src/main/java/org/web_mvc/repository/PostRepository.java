package org.web_mvc.repository;

import org.springframework.stereotype.Repository;
import org.web_mvc.exception.NotFoundException;
import org.web_mvc.model.Post;
import org.web_mvc.model.PostDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final Map<Long, PostDTO> postsDTO = new ConcurrentHashMap<>();
    private final AtomicLong postIdCounter = new AtomicLong(1);

    public List<PostDTO> all() {
        List<Long> nonRemovedIds = posts.values().stream()
                                        .filter(post -> !post.isRemoved())
                                        .map(Post::getId)
                                        .collect(Collectors.toList());
        return postsDTO.values().stream()
                        .filter(dto -> nonRemovedIds.contains(dto.getId()))
                        .collect(Collectors.toList());

    }

    public Optional<PostDTO> getById(long id) {
        return Optional.ofNullable(posts.get(id))
                .filter(post -> !post.isRemoved())
                .map(post -> postsDTO.get(id));
    }

    public PostDTO save(Post post) {
        if (post.getId() == 0) {
            post.setId(postIdCounter.getAndIncrement());
        } else {
            if (!posts.containsKey(post.getId())) {
                throw new IllegalArgumentException("Поста с id=" + post.getId() + " не существует");
            }

            if (posts.get(post.getId()).isRemoved()) {
                throw new NotFoundException("Пост с id=" + post.getId() + " был удален и не может быть обновлен");
            }
        }

        posts.put(post.getId(), post);
        postsDTO.put(post.getId(), new PostDTO(post.getId(), post.getContent()));
        return postsDTO.get(post.getId());
    }


    public void removeById(long id) {
        posts.get(id).setRemoved(true);
    }
}

