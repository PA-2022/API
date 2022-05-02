package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.repositories.ForumRepository;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("forums")
public class ForumController {
    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public Forum getForum(@PathVariable Long id){
        return forumRepository.getForumById(id);
    }

    @PostMapping("/add")
    @ResponseBody
    public Long createForum(@RequestBody Forum forum){
        return forumRepository.save(forum).getId();
    }

    @GetMapping("/all/limit/{limit}/offset/{offset}")
    @ResponseBody
    public List<Forum> getAllWithLimit(@PathVariable int limit, @PathVariable int offset){
        return forumRepository.findBy(PageRequest.of(offset, limit));
    }
}
