package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.Post;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.services.AuthService;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final AuthService authService;

    @Autowired
    public PostController(PostRepository postRepository, AuthService authService){
        this.postRepository = postRepository;
        this.authService = authService;
    }

    @PostMapping("/add")
    public Post addPost(@RequestBody Post post){
        System.out.println(post.getTitle());
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        post.setUserId(currentUser.getId());
        return this.postRepository.save(post);
    }

    @GetMapping("/{postId}")
    public Post getById(@PathVariable Long postId){
        Post post = this.postRepository.getPostById(postId);
        if(post == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find post");
        }
        return post;
    }

}
