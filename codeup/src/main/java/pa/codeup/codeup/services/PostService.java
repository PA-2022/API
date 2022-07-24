package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pa.codeup.codeup.dto.*;
import pa.codeup.codeup.entities.PostWithUserAndForum;
import pa.codeup.codeup.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final ContentPostRepository contentPostRepository;
    private final PostRepository postRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;
    private final PostVoteRepository postVoteRepository;
    private final AuthService authService;

    @Autowired
    public PostService(ContentPostRepository contentPostRepository, PostRepository postRepository, ForumRepository forumRepository, UserRepository userRepository, PostVoteRepository postVoteRepository, AuthService authService) {
        this.contentPostRepository = contentPostRepository;
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
        this.postVoteRepository = postVoteRepository;
        this.authService = authService;
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
        List<Post> posts;
        List<PostWithUserAndForum> postsWithUserAndForum = new ArrayList<>();
        if(forumId != null){
            Forum forum = this.forumRepository.getForumById(forumId);
            if(forum == null) {
                throw new Exception("Forum not found");
            }
            if(category.equals("Popular")) {
                posts = this.postRepository.    findAllByForumIdOrderByNoteDescCreationDateDesc(forumId, PageRequest.of(offset, limit));
            } else {
                posts = this.postRepository.findAllByForumIdOrderByCreationDateDesc(forumId, PageRequest.of(offset, limit));
            }
        } else {
            posts = this.postRepository.findAll();
        }
        for (Post post :posts) {
            User user = this.userRepository.getUserById(post.getUserId());
            Forum forum = this.forumRepository.getForumById(post.getForumId());
            User authUser = this.authService.getAuthUser();
            if (authUser != null) {
                PostVote vote = this.postVoteRepository.findPostVoteByPostIdAndUserId(post.getId(), authUser.getId()).orElse(null);
                if (vote != null) {
                    postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forum.getTitle(), forum.getColor(), true, vote.isUpvote()));
                } else {
                    postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forum.getTitle(), forum.getColor(), false, false));
                }
            } else {
                postsWithUserAndForum.add(new PostWithUserAndForum(post, user.getUsername(), user.getProfilePictureUrl(), forum.getTitle(), forum.getColor(), false, false));
            }
        }
        return postsWithUserAndForum;
    }
}
