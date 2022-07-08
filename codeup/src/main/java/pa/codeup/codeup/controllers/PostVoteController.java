package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.PostVote;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.repositories.PostVoteRepository;
import pa.codeup.codeup.services.AuthService;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("posts-vote")
public class PostVoteController {

    private PostVoteRepository postVoteRepository;
    private AuthService authService;
    @Autowired
    public PostVoteController(PostVoteRepository postVoteRepository, AuthService authService){
        this.postVoteRepository = postVoteRepository;
        this.authService = authService;
    }

    @GetMapping("/post/{id}")
    public void getUserVoteForPost(@PathVariable Long id) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        this.postVoteRepository.getPostVoteByPostIdAndUserId(id, currentUser.getId());
    }

    @PutMapping()
    public PostVote putUserVoteForPost(@RequestBody PostVote postVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        return this.postVoteRepository.saveAndFlush(postVote);
    }

    @DeleteMapping
    public boolean deleteVoteForPost(@RequestBody PostVote postVote) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        postVote.setUserId(currentUser.getId());
        this.postVoteRepository.delete(postVote);
        return true;
    }
}
