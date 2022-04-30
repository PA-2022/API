package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.repositories.ForumRepository;

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
}
