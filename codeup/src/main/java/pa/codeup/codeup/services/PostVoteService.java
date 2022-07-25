package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.CommentVoteDao;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.PostVoteDao;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.CommentVote;
import pa.codeup.codeup.entities.PostVote;
import pa.codeup.codeup.repositories.PostRepository;
import pa.codeup.codeup.repositories.PostVoteRepository;

import java.util.Optional;

@Service
public class PostVoteService {

    private final PostVoteRepository postVoteRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostVoteService(PostVoteRepository postVoteRepository, PostRepository postRepository) {
        this.postVoteRepository = postVoteRepository;
        this.postRepository = postRepository;
    }

    public Optional<PostVoteDao> getPostVoteByPostIdAndUserId(Long postId, Long userId) {
        return this.postVoteRepository.findPostVoteByPostIdAndUserId(postId, userId);
    }

    public PostVoteDao saveAndFlush(PostVoteDao postVote) {
        this.postVoteRepository.saveAndFlush(postVote);
        this.setPostNote(this.postRepository.getById(postVote.getPostId()));
        return postVote;
    }

    public void delete(PostVoteDao postVote) {
        this.postVoteRepository.delete(postVote);
        this.setPostNote(this.postRepository.getById(postVote.getPostId()));
    }

    public void setPostNote(Post comment) {
        comment.setNote(this.postVoteRepository.countAllByPostIdAndUpvote(comment.getId(), true)
                - this.postVoteRepository.countAllByPostIdAndUpvote(comment.getId(), false));
        this.postRepository.saveAndFlush(comment);
    }

    public PostVote putPostVote(PostVote postVote, UserDao currentUser) {
        postVote.setUserId(currentUser.getId());
        PostVoteDao exists = this.getPostVoteByPostIdAndUserId(postVote.getPostId(), currentUser.getId()).orElse(null);
        if(exists == null) {
            return this.saveAndFlush(postVote.createDao()).toEntity();
        }
        if(exists.isUpvote() == postVote.isUpvote()) {
            this.delete(exists);
            return null;
        }
        exists.setUpvote(postVote.isUpvote());
        return this.saveAndFlush(exists).toEntity();

    }
}
