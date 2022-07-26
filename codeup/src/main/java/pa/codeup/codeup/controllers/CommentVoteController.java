package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.CommentVoteDao;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.CommentVote;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.CommentVoteService;

import javax.validation.Valid;

import java.security.spec.ECField;

import static org.springframework.http.HttpStatus.NOT_FOUND;
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
    public ResponseEntity<CommentVote> getUserVoteForComment(@PathVariable @Valid Long id) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        CommentVoteDao commentVoteDao = this.commentVoteService.getCommentVoteByCommentIdAndUserId(id, currentUser.getId()).orElse(null);
        if(commentVoteDao == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentVoteDao.toEntity(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<CommentVote> putUserVoteForComment(@RequestBody @Valid CommentVote commentVote) {

        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        try{
            CommentVote cv = this.commentVoteService.putCommentVote(commentVote, currentUser);
            if(cv == null) {
                new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(cv, HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(NOT_FOUND, "Comment not found");
        }

    }

    @DeleteMapping
    public boolean deleteVoteForComment(@RequestBody @Valid CommentVoteDao commentVoteDao) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        commentVoteDao.setUserId(currentUser.getId());
        this.commentVoteService.delete(commentVoteDao);
        return true;
    }
}
