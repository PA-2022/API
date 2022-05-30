package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.entities.UserForumRelation;
import pa.codeup.codeup.repositories.ForumRepository;
import pa.codeup.codeup.repositories.UserForumRelationRepository;
import pa.codeup.codeup.repositories.UserRepository;
import pa.codeup.codeup.services.AuthService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user-forum-relation")
public class UserForumRelationController {

    private final AuthService authService;
    private final ForumRepository forumRepository;
    private final UserForumRelationRepository userForumRelationRepository;

    @Autowired
    public UserForumRelationController(AuthService authService, ForumRepository forumRepository,
                                       UserForumRelationRepository userForumRelationRepository) {
        this.authService = authService;
        this.forumRepository = forumRepository;
        this.userForumRelationRepository = userForumRelationRepository;
    }

    @GetMapping("/forum/{forumId}")
    public UserForumRelation getRelation(@PathVariable Long forumId) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        UserForumRelation userForumRelation = this.userForumRelationRepository.getByUserIdAndForumId(currentUser.getId(), forumId);
        if (userForumRelation == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find forum");
        }

        return userForumRelation;
    }

    @PostMapping("/add/forum/{forumId}")
    public UserForumRelation addRelation(@PathVariable Long forumId) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }
        Forum forum = this.forumRepository.getForumById(forumId);
        if (forum == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find forum");
        }
        UserForumRelation userForumRelation = new UserForumRelation();
        userForumRelation.setUserId(currentUser.getId());
        userForumRelation.setForumId(forum.getId());
        return this.userForumRelationRepository.save(userForumRelation);
    }

    @DeleteMapping("/delete/forum/{forumId}")
    public boolean deleteRelation(@PathVariable Long forumId) {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        UserForumRelation userForumRelation = this.userForumRelationRepository.getByUserIdAndForumId(currentUser.getId(), forumId);
        if (userForumRelation == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find forum");
        }

        this.userForumRelationRepository.delete(userForumRelation);
        return true;
    }

    @GetMapping("/all-by-logged-user")
    public List<UserForumRelation> getAllByLoggedUser() {
        User currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        return this.userForumRelationRepository.getAllByUserId(currentUser.getId());
    }
}
