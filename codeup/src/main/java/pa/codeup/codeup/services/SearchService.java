package pa.codeup.codeup.services;

import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.entities.Friend;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.entities.SearchEntity;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.dto.UserForumRelation;
import pa.codeup.codeup.entities.UserIsFriend;
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
    public FriendService friendService;

    public SearchService(UserRepository userRepository, PostRepository postRepository, AuthService authService, UserForumRelationRepository userForumRelationRepository, ForumRepository forumRepository, FriendService friendService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authService = authService;
        this.userForumRelationRepository = userForumRelationRepository;
        this.forumRepository = forumRepository;
        this.friendService = friendService;
    }

    public SearchEntity performSeach(String searchString){
        User currentUser = authService.getAuthUser();

        List<Post> randomPosts = new ArrayList<>();
        List<User> users;
        List<Post> subscribedPosts = new ArrayList<>();
        if(currentUser != null){
            users = userRepository.findAllByUsernameLikeAndIdNot("%"+searchString+"%", currentUser.getId()).stream().limit(10).collect(Collectors.toList());
            List <UserForumRelation> userForumRelations = userForumRelationRepository.getAllByUserId(currentUser.getId());
            List<Long> forumsIds = userForumRelations.stream().map(UserForumRelation::getForumId).collect(Collectors.toList());
            randomPosts = postRepository.findAllByTitleLikeOrContentLikeAndForumIdNotIn("%"+searchString+"%", "%"+searchString+"%", forumsIds).stream().limit(10).collect(Collectors.toList());
            subscribedPosts = postRepository.findAllByTitleLikeOrContentLikeAndForumIdIn("%"+searchString+"%", "%"+searchString+"%", forumsIds).stream().limit(10).collect(Collectors.toList());
        }
        else {
            users = userRepository.findAllByUsernameLike("%"+searchString+"%").stream().limit(10).collect(Collectors.toList());
            randomPosts = postRepository.findAllByTitleLikeOrContentLike("%"+searchString+"%", "%"+searchString+"%").stream().limit(10).collect(Collectors.toList());
        }

        List<UserIsFriend> usersAreFriends = new ArrayList<>();
        for (User user: users) {
            Friend isFriend = currentUser != null ? this.friendService.getFriend(currentUser, user.getId()) : null;
            UserIsFriend uf = new UserIsFriend(user, isFriend != null, isFriend != null && isFriend.isAccepted());
            usersAreFriends.add(uf);
        }
        List<PostWithUserAndForum> randomPostsWithUserAndForum = new ArrayList<>();
        for (Post post: randomPosts) {
            User user = this.userRepository.getUserById(post.getUserId());
            String forumname = this.forumRepository.getForumById(post.getForumId()).getTitle();
            randomPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumname));
        }

        List<PostWithUserAndForum> subscribedPostsWithUserAndForum = new ArrayList<>();
        for (Post post: subscribedPosts) {
            User user = this.userRepository.getUserById(post.getUserId());
            String forumname = this.forumRepository.getForumById(post.getForumId()).getTitle();
            subscribedPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumname));
        }
        return new SearchEntity(usersAreFriends, randomPostsWithUserAndForum, subscribedPostsWithUserAndForum);
    }

    public List<PostWithUserAndForum> performLightSearch(String searchString) {
        List<Post> posts = this.postRepository.findAllByTitleLikeOrContentLike("%"+searchString+"%", "%"+searchString+"%");
        List<PostWithUserAndForum> postWithUserAndForums = new ArrayList<>();
        for (Post post: posts) {
            User user = this.userRepository.getUserById(post.getUserId());
            String forumname = this.forumRepository.getForumById(post.getForumId()).getTitle();
            postWithUserAndForums.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumname));
        }
        return postWithUserAndForums.stream().limit(6).toList();
    }

}
