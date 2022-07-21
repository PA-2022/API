package pa.codeup.codeup.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.entities.SearchEntity;
import pa.codeup.codeup.services.SearchService;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{searchString}")
    public SearchEntity performSearch(@PathVariable String searchString) {
        return searchService.performSeach(searchString);
    }
    @GetMapping("/light-search/{searchString}")
    public List<PostWithUserAndForum> performLightSearch(@PathVariable String searchString) {
        return searchService.performLightSearch(searchString);
    }
}
