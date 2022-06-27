package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.Post;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.ForumRepository;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.services.AuthService;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final ForumRepository forumRepository;
    private final AuthService authService;

    @Autowired
    public PostController(PostRepository postRepository, ForumRepository forumRepository, AuthService authService){
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.authService = authService;
    }

    @PostMapping("/add")
    public Post addPost(@RequestBody Post post){
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

    @GetMapping("/forum/{forumId}")
    public List<Post> getByForumId(@PathVariable Long forumId){
        if(this.forumRepository.getForumById(forumId) == null){
            throw new ResponseStatusException(NOT_FOUND, "Forum not found");
        }
        return this.postRepository.getPostByForumId(forumId);
    }

    @PutMapping()
    public Post updatePost(@RequestBody Post post){
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }
        if (!Objects.equals(currentUser.getId(), this.postRepository.getPostById(post.getId()).getUserId())) {
            throw new ResponseStatusException(UNAUTHORIZED, "User cant edit this post");
        }
        return this.postRepository.saveAndFlush(post);
    }

    @DeleteMapping("/{postId}")
    public boolean deletePost(@PathVariable Long postId){
        User currentUser = authService.getAuthUser();
        Post post = this.postRepository.getPostById(postId);
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }
        if (!Objects.equals(currentUser.getId(), this.postRepository.getPostById(post.getId()).getUserId())) {
            throw new ResponseStatusException(UNAUTHORIZED, "User cant edit this post");
        }

        this.postRepository.deleteById(postId);

        throw new ResponseStatusException(OK, "Post deleted");
    }

}
