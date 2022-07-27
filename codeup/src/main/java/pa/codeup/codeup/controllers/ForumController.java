package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.AuthEntity;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.ForumService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;
    private final AuthService authService;

    @Autowired
    public ForumController(ForumService forumService, AuthService authService) {
        this.forumService = forumService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForum(@PathVariable @Valid Long id) {
        return new ResponseEntity<>(this.forumService.getForumById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> createForum(@RequestBody @Valid Forum forum) {
        UserDao user = this.authService.getAuthUser();
        if(user != null) {
            try{
                return new ResponseEntity<>(this.forumService.save(forum).getId(), HttpStatus.OK);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Forum already exists");
            }
        } else {
            throw new ResponseStatusException(UNAUTHORIZED);
        }
    }

    @GetMapping("/all/limit/{limit}/offset/{offset}")
    public ResponseEntity<List<Forum>> getAllWithLimit(@PathVariable int limit, @PathVariable int offset) {
        return new ResponseEntity<>(this.forumService.findByOrderByIdDesc(offset, limit), HttpStatus.OK);
    }
}
