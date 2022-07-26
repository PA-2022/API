package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.PostVoteDao;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.PostVote;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.PostVoteService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("posts-vote")
public class PostVoteController {

    private final PostVoteService postVoteService;
    private final AuthService authService;
    @Autowired
    public PostVoteController(PostVoteService postVoteRepository, AuthService authService){
        this.postVoteService = postVoteRepository;
        this.authService = authService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostVote> getUserVoteForPost(@PathVariable @Valid Long id) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        PostVoteDao postVoteDao = this.postVoteService.getPostVoteByPostIdAndUserId(id, currentUser.getId()).orElse(null);
        if(postVoteDao == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postVoteDao.toEntity(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<PostVote> putUserVoteForPost(@RequestBody @Valid PostVote postVote) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        PostVote pv = this.postVoteService.putPostVote(postVote, currentUser);
        if(pv == null) {
            new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(pv, HttpStatus.OK);
    }

    @DeleteMapping
    public boolean deleteVoteForPost(@RequestBody @Valid PostVote postVote) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        this.postVoteService.delete(postVote.createDao());
        return true;
    }
}
