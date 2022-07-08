package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.CommentVote;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.CommentVoteRepository;
import pa.codeup.codeup.services.AuthService;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("comment-votes")
public class CommentVoteController {

    private CommentVoteRepository commentVoteRepository;
    private AuthService authService;
    @Autowired
    public CommentVoteController(CommentVoteRepository commentVoteRepository, AuthService authService){
        this.commentVoteRepository = commentVoteRepository;
        this.authService = authService;
    }

    @GetMapping("/comment/{id}")
    public void getUserVoteForComment(@PathVariable Long id) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        this.commentVoteRepository.getCommentVoteByCommentIdAndUserId(id, currentUser.getId());
    }

    @PutMapping()
    public CommentVote putUserVoteForComment(@RequestBody CommentVote commentVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        commentVote.setUserId(currentUser.getId());
        return this.commentVoteRepository.saveAndFlush(commentVote);
    }

    @DeleteMapping
    public boolean deleteVoteForComment(@RequestBody CommentVote commentVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        commentVote.setUserId(currentUser.getId());
        this.commentVoteRepository.delete(commentVote);
        return true;
    }
}
