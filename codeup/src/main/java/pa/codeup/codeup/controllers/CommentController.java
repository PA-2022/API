package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.Comment;
import pa.codeup.codeup.entities.CommentWithUser;
import pa.codeup.codeup.entities.PostComment;
import pa.codeup.codeup.repositories.CommentRepository;
import pa.codeup.codeup.repositories.UserRepository;
import pa.codeup.codeup.services.AuthService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("comments")
public class CommentController {

    private CommentRepository commentRepository;
    private AuthService authService;
    private UserRepository userRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository, AuthService authService, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Comment getComment(@PathVariable Long id) {
        return commentRepository.getCommentById(id);
    }

    @PostMapping
    @ResponseBody
    public Long addComment(@RequestBody Comment comment) {
        comment.setUserId(this.authService.getAuthUser().getId());
        return commentRepository.save(comment).getId();
    }

    @GetMapping("/comment/post/{postId}")
    @ResponseBody
    public List<PostComment> getPostComments(@PathVariable Long postId) {
        List<PostComment> postComments = new ArrayList<>();
        List<Comment> comments = commentRepository.getAllByPostIdAndCommentParentIdIsNull(postId);
        comments.forEach(comment -> {
            List<CommentWithUser> responses = new ArrayList<>();
            this.commentRepository.getAllByCommentParentId(comment.getId()).forEach(simpleResponse -> {
                responses.add(new CommentWithUser(simpleResponse, this.userRepository.getUserById(simpleResponse.getUserId())));
            });

            postComments.add(new PostComment(new CommentWithUser(comment, this.userRepository.getUserById(comment.getUserId())), responses));
        });

        return postComments;
    }

    @GetMapping("/comment/post/{postId}/count")
    @ResponseBody
    public int getPostCommentsCount(@PathVariable Long postId) {
        return commentRepository.countCommentByPostId(postId);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public boolean deletePostComment(@PathVariable Long commentId){
        Comment comment = this.commentRepository.getCommentById(commentId);
        if(comment.getUserId().intValue() == this.authService.getAuthUser().getId().intValue()){
            this.commentRepository.deleteCommentById(commentId);
            this.commentRepository.deleteAllByCommentParentId(commentId);
            return true;
        }
        throw new ResponseStatusException(UNAUTHORIZED, "User doesnt have the right to delete comment");
    }

    @PutMapping()
    @ResponseBody
    public Long editComment(@RequestBody Comment comment) {
        return commentRepository.saveAndFlush(comment).getId();
    }
}
