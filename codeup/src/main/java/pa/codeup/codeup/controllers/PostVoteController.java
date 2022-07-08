package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.PostVote;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.PostVoteService;

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
    public void getUserVoteForPost(@PathVariable Long id) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        this.postVoteService.getCommentVoteByCommentIdAndUserId(id, currentUser.getId());
    }

    @PutMapping()
    public PostVote putUserVoteForPost(@RequestBody PostVote postVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        return this.postVoteService.saveAndFlush(postVote);
    }

    @DeleteMapping
    public boolean deleteVoteForPost(@RequestBody PostVote postVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        this.postVoteService.delete(postVote);
        return true;
    }
}
