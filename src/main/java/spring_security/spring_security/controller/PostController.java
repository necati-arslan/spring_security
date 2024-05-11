package spring_security.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security.spring_security.Repository.PostRepository;
import spring_security.spring_security.model.Post;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository posts;

    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping
    public Iterable findAll() {
        return posts.findAll();
    }

    @GetMapping("{id}")
    public Post findById(@PathVariable("id") Post post) {
        return post;
    }

}