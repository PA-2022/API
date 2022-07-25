package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.dto.ForumDao;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.services.ForumService;

import java.util.List;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForum(@PathVariable Long id) {
        return new ResponseEntity<>(this.forumService.getForumById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> createForum(@RequestBody Forum forum) {
        return new ResponseEntity<>(this.forumService.save(forum).getId(), HttpStatus.OK);
    }

    @GetMapping("/all/limit/{limit}/offset/{offset}")
    public ResponseEntity<List<Forum>> getAllWithLimit(@PathVariable int limit, @PathVariable int offset) {
        return new ResponseEntity<>(this.forumService.findByOrderByIdDesc(offset, limit), HttpStatus.OK);
    }
}
