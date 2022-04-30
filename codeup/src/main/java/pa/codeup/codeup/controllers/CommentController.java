package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.Comment;
import pa.codeup.codeup.repositories.CommentRepository;

import java.util.List;

@Controller
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public Comment getComment(@PathVariable Long id) {
        return commentRepository.getCommentById(id);
    }

    @PostMapping
    @ResponseBody
    public Long addComment(@RequestBody Comment comment) {
        return commentRepository.save(comment).getId();
    }

    @GetMapping("/comment/post/{postId}")
    @ResponseBody
    public List<Comment> getPostComments(@PathVariable Long postId) {
        return commentRepository.getAllByPostId(postId);
    }

    @GetMapping("/comment/post/{postId}/count")
    @ResponseBody
    public int getPostCommentsCount(@PathVariable Long postId) {
        return commentRepository.countCommentByPostId(postId);
    }

}
