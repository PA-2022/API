package pa.codeup.codeup.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pa.codeup.codeup.entities.SearchEntity;
import pa.codeup.codeup.services.SearchService;

@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{searchString}")
    public SearchEntity performSearch(@PathVariable String searchString) {
        return searchService.performSeach(searchString);
    }
}
