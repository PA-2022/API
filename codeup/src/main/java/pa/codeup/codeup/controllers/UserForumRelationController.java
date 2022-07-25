package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.dto.UserForumRelationDao;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.entities.UserForumRelation;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.UserForumRelationService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user-forum-relation")
public class UserForumRelationController {

    private final AuthService authService;
    private final UserForumRelationService userForumRelationService;

    @Autowired
    public UserForumRelationController(AuthService authService, UserForumRelationService userForumRelationService) {
        this.authService = authService;
        this.userForumRelationService = userForumRelationService;
    }

    @GetMapping("/forum/{forumId}")
    public ResponseEntity<UserForumRelation> getRelation(@PathVariable Long forumId) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        UserForumRelation userForumRelation = this.userForumRelationService.getByUserIdAndForumId(currentUser.getId(), forumId);
        if (userForumRelation == null) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find forum");
        }

        return new ResponseEntity<>(userForumRelation, OK);
    }

    @PostMapping("/add/forum/{forumId}")
    public ResponseEntity<UserForumRelation> addRelation(@PathVariable Long forumId) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        return new ResponseEntity<>(this.userForumRelationService.addRelation(forumId, currentUser), OK);
    }

    @DeleteMapping("/delete/forum/{forumId}")
    public boolean deleteRelation(@PathVariable Long forumId) {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }
        if(!this.userForumRelationService.deleteRelation(forumId, currentUser.getId())) {
            throw new ResponseStatusException(NO_CONTENT, "Unable to find forum");
        }

        return true;
    }

    @GetMapping("/all-by-logged-user")
    public ResponseEntity<List<UserForumRelationDao>> getAllByLoggedUser() {
        UserDao currentUser = authService.getAuthUser();
        if (currentUser == null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User not connected");
        }

        return new ResponseEntity<>(this.userForumRelationService.getAllByUserId(currentUser.getId()), OK);
    }
}
