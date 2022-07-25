package pa.codeup.codeup.services;

import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.*;
import pa.codeup.codeup.entities.Friend;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.entities.SearchEntity;
import pa.codeup.codeup.entities.UserIsFriend;
import pa.codeup.codeup.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final AuthService authService;
    public final UserForumRelationRepository userForumRelationRepository;
    public final ForumRepository forumRepository;
    public final FriendService friendService;
    public final PostVoteRepository postVoteRepository;

    public SearchService(UserRepository userRepository, PostRepository postRepository, AuthService authService, UserForumRelationRepository userForumRelationRepository, ForumRepository forumRepository, FriendService friendService, PostVoteRepository postVoteRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authService = authService;
        this.userForumRelationRepository = userForumRelationRepository;
        this.forumRepository = forumRepository;
        this.friendService = friendService;
        this.postVoteRepository = postVoteRepository;
    }

    public SearchEntity performSeach(String searchString) {
        UserDao currentUser = authService.getAuthUser();

        List<Post> randomPosts = new ArrayList<>();
        List<UserDao> users;
        List<Post> subscribedPosts = new ArrayList<>();
        if (currentUser != null) {
            users = userRepository.findAllByUsernameLikeAndIdNot("%" + searchString + "%", currentUser.getId()).stream().limit(10).collect(Collectors.toList());
            List<UserForumRelation> userForumRelations = userForumRelationRepository.getAllByUserId(currentUser.getId());
            List<Long> forumsIds = userForumRelations.stream().map(UserForumRelation::getForumId).collect(Collectors.toList());
            randomPosts = postRepository.findAllByTitleLikeAndForumIdNotInAndUserIdNot("%" + searchString + "%",  forumsIds, currentUser.getId()).stream().limit(10).collect(Collectors.toList());
            subscribedPosts = postRepository.findAllByTitleLikeAndForumIdInAndUserIdNot("%" + searchString + "%", forumsIds, currentUser.getId()).stream().limit(10).collect(Collectors.toList());
        } else {
            users = userRepository.findAllByUsernameLike("%" + searchString + "%").stream().limit(10).collect(Collectors.toList());
            randomPosts = postRepository.findAllByTitleLikeOrContentLike("%" + searchString + "%", "%" + searchString + "%").stream().limit(10).collect(Collectors.toList());
        }

        List<UserIsFriend> usersAreFriends = new ArrayList<>();
        for (UserDao user : users) {
            Friend isFriend = currentUser != null ? this.friendService.getFriend(currentUser, user.getId()) : null;
            UserIsFriend uf = new UserIsFriend(user, isFriend != null, isFriend != null && isFriend.isAccepted());
            usersAreFriends.add(uf);
        }
        List<PostWithUserAndForum> randomPostsWithUserAndForum = new ArrayList<>();
        for (Post post : randomPosts) {
            UserDao user = this.userRepository.getUserById(post.getUserId());
            ForumDao forumDao = this.forumRepository.getForumById(post.getForumId());
            if (currentUser != null) {
                PostVote vote = this.postVoteRepository.findPostVoteByPostIdAndUserId(post.getId(), currentUser.getId()).orElse(null);
                if (vote != null) {
                    randomPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), true, vote.isUpvote()));
                } else {
                    randomPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
                }
            } else {
                randomPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
            }
        }
        List<PostWithUserAndForum> subscribedPostsWithUserAndForum = new ArrayList<>();
        for (Post post : subscribedPosts) {
            UserDao user = this.userRepository.getUserById(post.getUserId());
            ForumDao forumDao = this.forumRepository.getForumById(post.getForumId());
            PostVote vote = this.postVoteRepository.findPostVoteByPostIdAndUserId(post.getId(), currentUser.getId()).orElse(null);
            if (vote != null) {
                subscribedPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), true, vote.isUpvote()));
            } else {
                subscribedPostsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
            }
        }
        return new SearchEntity(usersAreFriends, randomPostsWithUserAndForum, subscribedPostsWithUserAndForum);
    }

    public List<PostWithUserAndForum> performLightSearch(String searchString) {
        List<Post> posts = this.postRepository.findAllByTitleLikeOrContentLike("%" + searchString + "%", "%" + searchString + "%");
        List<PostWithUserAndForum> postWithUserAndForums = new ArrayList<>();
        for (Post post : posts) {
            UserDao user = this.userRepository.getUserById(post.getUserId());
            ForumDao forumDao = this.forumRepository.getForumById(post.getForumId());
            UserDao authUser = this.authService.getAuthUser();
            if (authUser != null) {
                PostVote vote = this.postVoteRepository.findPostVoteByPostIdAndUserId(post.getId(), authUser.getId()).orElse(null);
                if (vote != null) {
                    postWithUserAndForums.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), true, vote.isUpvote()));
                } else {
                    postWithUserAndForums.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
                }
            } else {
                postWithUserAndForums.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
            }
        }
        return postWithUserAndForums.stream().limit(6).toList();
    }

}
