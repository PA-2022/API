package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import pa.codeup.codeup.dto.ContentPost;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.PostContent;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.repositories.ContentPostRepository;
import pa.codeup.codeup.repositories.ForumRepository;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.CodeService;
import pa.codeup.codeup.services.PostService;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final ForumRepository forumRepository;
    private final AuthService authService;
    private final PostService postService;
    private final ContentPostRepository contentPostRepository;

    @Autowired
    public PostController(PostRepository postRepository, ForumRepository forumRepository, AuthService authService, PostService postService, ContentPostRepository contentPostRepository) {
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.authService = authService;
        this.postService = postService;
        this.contentPostRepository = contentPostRepository;
    }

    @PostMapping("/add")
    public Post addPost(@RequestBody PostContent postContent) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        postContent.getPost().setUserId(currentUser.getId());
        return this.postService.addContentAndPost(postContent.getPost(), postContent.getContentPost());
    }

    @GetMapping("/{postId}")
    public Post getById(@PathVariable Long postId){
        Post post = this.postRepository.getPostById(postId);
        if(post == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find post");
        }
        return post;
    }

    @GetMapping("/{postId}/content")
    public List<ContentPost> getContentById(@PathVariable Long postId){
        
        List<ContentPost> contentPosts = this.contentPostRepository.findAllByPostId(postId);
        if(contentPosts == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find post");
        }
        return contentPosts;
    }

    @GetMapping("/forum/{forumId}")
    public List<Post> getByForumId(@PathVariable Long forumId){
        if(this.forumRepository.getForumById(forumId) == null){
            throw new ResponseStatusException(NOT_FOUND, "Forum not found");
        }
        return this.postRepository.getPostByForumId(forumId);
    }

    @GetMapping("post-list/forum/{forumId}/category/{category}/offset/{offset}/limit/{limit}")
    public ResponseEntity<List<PostWithUserAndForum>> getListPostByForum(@PathVariable Long forumId, @PathVariable String category, @PathVariable int offset, @PathVariable int limit){
        try {
            if(offset == 0 || limit == 0) {
                limit = 10;
            }
            offset = offset/limit;
            return new ResponseEntity<>(this.postService.getPostWithUserAndForumList(forumId, category, offset, limit), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Forum not found");
        }
    }

    @GetMapping("post-list/category/{category}/offset/{offset}/limit/{limit}")
    public ResponseEntity<List<PostWithUserAndForum>> getListPost(@PathVariable String category, @PathVariable int offset, @PathVariable int limit){
        try {
            if(limit == 0) {
                limit = 10;
            }
            offset = offset/limit;
            return new ResponseEntity<>(this.postService.getPostWithUserAndForumList(null, category , offset, limit), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Forum not found");
        }
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
            throw new ResponseStatusException(UNAUTHORIZED, "User cant delete this post");
        }

        this.postRepository.deleteById(postId);

        throw new ResponseStatusException(OK, "Post deleted");
    }

}
