package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.PostVote;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.PostVoteService;

import java.util.Optional;

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
    public Optional<PostVote> getUserVoteForPost(@PathVariable Long id) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        return this.postVoteService.getPostVoteByPostIdAndUserId(id, currentUser.getId());
    }

    @PutMapping()
    public PostVote putUserVoteForPost(@RequestBody PostVote postVote) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        PostVote exists = this.postVoteService.getPostVoteByPostIdAndUserId(postVote.getPostId(), currentUser.getId()).orElse(null);
        if(exists == null) {
            return this.postVoteService.saveAndFlush(postVote);
        }
        if(exists.isUpvote() == postVote.isUpvote()) {
         this.postVoteService.delete(exists);
         return null;
        }
        exists.setUpvote(postVote.isUpvote());
        return this.postVoteService.saveAndFlush(exists);
    }

    @DeleteMapping
    public boolean deleteVoteForPost(@RequestBody PostVote postVote) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        this.postVoteService.delete(postVote);
        return true;
    }
}
