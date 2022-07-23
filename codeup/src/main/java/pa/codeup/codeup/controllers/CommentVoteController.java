package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.CommentVote;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.CommentVoteService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("comment-votes")
public class CommentVoteController {

    private final CommentVoteService commentVoteService;
    private final AuthService authService;
    @Autowired
    public CommentVoteController(CommentVoteService commentVoteService, AuthService authService){
        this.commentVoteService = commentVoteService;
        this.authService = authService;
    }

    @GetMapping("/comment/{id}")
    public Optional<CommentVote> getUserVoteForComment(@PathVariable Long id) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        return this.commentVoteService.getCommentVoteByCommentIdAndUserId(id, currentUser.getId());
    }

    @PutMapping()
    public CommentVote putUserVoteForComment(@RequestBody CommentVote commentVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        commentVote.setUserId(currentUser.getId());
        return this.commentVoteService.saveAndFlush(commentVote);
    }

    @DeleteMapping
    public boolean deleteVoteForComment(@RequestBody CommentVote commentVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        commentVote.setUserId(currentUser.getId());
        this.commentVoteService.delete(commentVote);
        return true;
    }
}
