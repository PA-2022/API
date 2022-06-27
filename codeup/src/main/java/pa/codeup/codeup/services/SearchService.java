package pa.codeup.codeup.services;

import org.springframework.stereotype.Service;
import pa.codeup.codeup.entities.Post;
import pa.codeup.codeup.entities.SearchEntity;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.entities.UserForumRelation;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.repositories.UserForumRelationRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public UserRepository userRepository;
    public PostRepository postRepository;
    public AuthService authService;
    public UserForumRelationRepository userForumRelationRepository;

    public SearchService(UserRepository userRepository, PostRepository postRepository, AuthService authService, UserForumRelationRepository userForumRelationRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authService = authService;
        this.userForumRelationRepository = userForumRelationRepository;
    }

    public SearchEntity performSeach(String searchString){
        User currentUser = authService.getAuthUser();
        List <UserForumRelation> userForumRelations = userForumRelationRepository.getAllByUserId(currentUser.getId());
        List<Long> forumsIds = userForumRelations.stream().map(UserForumRelation::getForumId).collect(Collectors.toList());

        List<User> users = userRepository.findAllByUsernameLike(searchString);
        List<Post> randomPosts = postRepository.findAllByTitleLikeOrContentLike(searchString, searchString);
        List<Post> subscribedPosts = postRepository.findAllByTitleLikeOrContentLikeAndForumIdIn(searchString, searchString, forumsIds);

        return new SearchEntity(users, randomPosts, subscribedPosts);
    }

}
