package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.repositories.ForumRepository;

import java.util.List;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private ForumRepository forumRepository;

    @Autowired
    public ForumController(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @GetMapping("/{id}")
    public Forum getForum(@PathVariable Long id) {
        return forumRepository.getForumById(id);
    }

    @PostMapping("/add")
    public Long createForum(@RequestBody Forum forum) {
        return forumRepository.save(forum).getId();
    }

    @GetMapping("/all/limit/{limit}/offset/{offset}")
    public List<Forum> getAllWithLimit(@PathVariable int limit, @PathVariable int offset) {
        return forumRepository.findByOrderByIdDesc(PageRequest.of(offset, limit));
    }
}
