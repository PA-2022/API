package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.Post;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.services.AuthService;

import static org.springframework.http.HttpStatus.*;

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

    @PutMapping()
    @ResponseBody
    public Long editComment(@RequestBody Post post) {
        return postRepository.saveAndFlush(post).getId();
    }

    @DeleteMapping("/post/{postId}")
    @ResponseBody
    public boolean deletePostComment(@PathVariable Long postId){
        Post post = this.postRepository.getPostById(postId);
        if(post.getUserId().intValue() == this.authService.getAuthUser().getId().intValue()){
            this.postRepository.deletePostById(postId);
            return true;
        }
        throw new ResponseStatusException(UNAUTHORIZED, "User doesnt have the right to delete comment");
    }

}
