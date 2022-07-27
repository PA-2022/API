package pa.codeup.codeup.controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SearchEntity> performSearch(@PathVariable String searchString) {
        return new ResponseEntity<>(searchService.performSeach(searchString), HttpStatus.OK);
    }
    @GetMapping("/light-search/{searchString}")
    public ResponseEntity<List<PostWithUserAndForum>> performLightSearch(@PathVariable String searchString) {
        return new ResponseEntity<>(searchService.performLightSearch(searchString), HttpStatus.OK);
    }
}
