package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pa.codeup.codeup.dto.*;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final ContentPostRepository contentPostRepository;
    private final PostRepository postRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;
    private final PostVoteRepository postVoteRepository;
    private final AuthService authService;
    private final FriendService friendService;
    private final UserForumRelationRepository userForumRelationRepository;

    @Autowired
    public PostService(ContentPostRepository contentPostRepository, PostRepository postRepository, ForumRepository forumRepository, UserRepository userRepository, PostVoteRepository postVoteRepository, AuthService authService, FriendService friendService, UserForumRelationRepository userForumRelationRepository) {
        this.contentPostRepository = contentPostRepository;
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
        this.postVoteRepository = postVoteRepository;
        this.authService = authService;
        this.friendService = friendService;
        this.userForumRelationRepository = userForumRelationRepository;
    }

    public Post addContentAndPost(Post post, ContentPost[] contentPost) {

        Post currentPost = this.postRepository.save(post);

        for (ContentPost content : contentPost) {
            content.setPostId(currentPost.getId());
            this.contentPostRepository.save(content);
        }

        return currentPost;
    }

    public List<PostWithUserAndForum> getPostWithUserAndForumList(Long forumId, String category, int offset, int limit) throws Exception {
        List<Post> posts = new ArrayList<>();
        List<PostWithUserAndForum> postsWithUserAndForum = new ArrayList<>();
        //forum page
        if (forumId != null) {
            ForumDao forumDao = this.forumRepository.getForumById(forumId);
            if (forumDao == null) {
                throw new Exception("Forum not found");
            }
            if (category.equals("Popular")) {
                posts = this.postRepository.findAllByForumIdOrderByNoteDescCreationDateDesc(forumId, PageRequest.of(offset, limit));
            } else {
                posts = this.postRepository.findAllByForumIdOrderByCreationDateDesc(forumId, PageRequest.of(offset, limit));
            }
        }
        //homepage
        else {
            UserDao user = this.authService.getAuthUser();
            List<Long> friends = new ArrayList<>();
            List<Long> forums = new ArrayList<>();
            //logged
            if(user != null) {
                friends = this.friendService.getUserFriends(user.getId());
                forums = this.userForumRelationRepository.getAllByUserId(user.getId()).stream().map(UserForumRelation::getForumId).collect(Collectors.toList());
                if (category.equals("Popular")) {
                    posts = this.postRepository.findAllByUserIdInOrForumIdInOrderByNoteDescCreationDateDesc(friends, forums, PageRequest.of(offset, limit));
                } else {
                    posts = this.postRepository.findAllByUserIdInOrForumIdInOrderByCreationDateDesc(friends, forums, PageRequest.of(offset, limit));
                }
            }
            //unlogged
            if (user == null || (forums.size() == 0 ||friends.size() == 0)) {
                if (category.equals("Popular")) {
                    posts = this.postRepository.findAllByIdNotNullOrderByNoteDescCreationDateDesc(PageRequest.of(offset, limit));
                } else {
                    posts = this.postRepository.findAllByIdNotNullOrderByCreationDateDesc(PageRequest.of(offset, limit));
                }
            }

        }
        for (Post post : posts) {
            UserDao user = this.userRepository.getUserById(post.getUserId());
            ForumDao forumDao = this.forumRepository.getForumById(post.getForumId());
            UserDao authUser = this.authService.getAuthUser();
            if (authUser != null) {
                PostVote vote = this.postVoteRepository.findPostVoteByPostIdAndUserId(post.getId(), authUser.getId()).orElse(null);
                if (vote != null) {
                    postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), true, vote.isUpvote()));
                } else {
                    postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
                }
            } else {
                postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
            }
        }
        return postsWithUserAndForum;
    }

    public List<PostWithUserAndForum> getUserPosts(Long userId, String category, int offset, int limit) throws Exception {
        if(this.userRepository.getUserById(userId) == null) {
            throw new Exception("User not found");
        }

        List<Post> posts = new ArrayList<>();
        if (category.equals("Popular")) {
            posts = this.postRepository.findAllByUserIdOrderByNoteDescCreationDateDesc(userId, PageRequest.of(offset, limit));
        } else {
            posts = this.postRepository.findAllByUserIdOrderByCreationDateDesc(userId, PageRequest.of(offset, limit));
        }

        List<PostWithUserAndForum> postsWithUserAndForum = new ArrayList<>();

        for (Post post : posts) {
            UserDao user = this.userRepository.getUserById(post.getUserId());
            ForumDao forumDao = this.forumRepository.getForumById(post.getForumId());
            UserDao authUser = this.authService.getAuthUser();
            if (authUser != null) {
                PostVote vote = this.postVoteRepository.findPostVoteByPostIdAndUserId(post.getId(), authUser.getId()).orElse(null);
                if (vote != null) {
                    postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), true, vote.isUpvote()));
                } else {
                    postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
                }
            } else {
                postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forumDao.getTitle(), forumDao.getColor(), false, false));
            }
        }
        return postsWithUserAndForum;


    }
}
