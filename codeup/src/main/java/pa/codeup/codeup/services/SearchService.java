package pa.codeup.codeup.services;

import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.entities.SearchEntity;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.dto.UserForumRelation;
import pa.codeup.codeup.repositories.ForumRepository;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.repositories.UserForumRelationRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public UserRepository userRepository;
    public PostRepository postRepository;
    public AuthService authService;
    public UserForumRelationRepository userForumRelationRepository;
    public ForumRepository forumRepository;

    public SearchService(UserRepository userRepository, PostRepository postRepository, AuthService authService, UserForumRelationRepository userForumRelationRepository, ForumRepository forumRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authService = authService;
        this.userForumRelationRepository = userForumRelationRepository;
        this.forumRepository = forumRepository;
    }

    public SearchEntity performSeach(String searchString){
        User currentUser = authService.getAuthUser();

        List<User> users = userRepository.findAllByUsernameLike("%"+searchString+"%").stream().limit(10).collect(Collectors.toList());
        List<Post> randomPosts = new ArrayList<>();
        List<Post> subscribedPosts = new ArrayList<>();
        if(currentUser != null){
            List <UserForumRelation> userForumRelations = userForumRelationRepository.getAllByUserId(currentUser.getId());
            List<Long> forumsIds = userForumRelations.stream().map(UserForumRelation::getForumId).collect(Collectors.toList());
            randomPosts = postRepository.findAllByTitleLikeOrContentLikeAndForumIdNotIn("%"+searchString+"%", "%"+searchString+"%", forumsIds).stream().limit(10).collect(Collectors.toList());
            subscribedPosts = postRepository.findAllByTitleLikeOrContentLikeAndForumIdIn("%"+searchString+"%", "%"+searchString+"%", forumsIds).stream().limit(10).collect(Collectors.toList());
        }
        else {
            randomPosts = postRepository.findAllByTitleLikeOrContentLike("%"+searchString+"%", "%"+searchString+"%").stream().limit(10).collect(Collectors.toList());
        }
        List<PostWithUserAndForum> randomPostsWithUserAndForum = new ArrayList<>();
        for (Post post: randomPosts) {
            String username = this.userRepository.getUserById(post.getUserId()).getUsername();
            String forumname = this.forumRepository.getForumById(post.getForumId()).getTitle();
            randomPostsWithUserAndForum.add(new PostWithUserAndForum(post, username, forumname));
        }

        List<PostWithUserAndForum> subscribedPostsWithUserAndForum = new ArrayList<>();
        for (Post post: subscribedPosts) {
            String username = this.userRepository.getUserById(post.getUserId()).getUsername();
            String forumname = this.forumRepository.getForumById(post.getForumId()).getTitle();
            subscribedPostsWithUserAndForum.add(new PostWithUserAndForum(post, username, forumname));
        }
        return new SearchEntity(users, randomPostsWithUserAndForum, subscribedPostsWithUserAndForum);
    }

    public List<PostWithUserAndForum> performLightSearch(String searchString) {
        List<Post> posts = this.postRepository.findAllByTitleLikeOrContentLike("%"+searchString+"%", "%"+searchString+"%");
        List<PostWithUserAndForum> postWithUserAndForums = new ArrayList<>();
        for (Post post: posts) {
            String username = this.userRepository.getUserById(post.getUserId()).getUsername();
            String forumname = this.forumRepository.getForumById(post.getForumId()).getTitle();
            postWithUserAndForums.add(new PostWithUserAndForum(post, username, forumname));
        }
        return postWithUserAndForums.stream().limit(6).toList();
    }

}
