package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment-votes")
public class CommentVoteController {

    @Autowired
    public CommentVoteController(){

    }
}
