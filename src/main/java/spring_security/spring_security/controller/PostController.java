package spring_security.spring_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security.spring_security.Repository.PostRepository;
import spring_security.spring_security.model.Post;


import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAllPost() {
        return new ResponseEntity<List<Post>>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Post findById(@PathVariable("id") Post post) {
        return post;
    }

}