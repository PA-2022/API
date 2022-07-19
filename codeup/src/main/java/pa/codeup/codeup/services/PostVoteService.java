package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.PostVote;
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

    public Optional<List<PostVote>> getCommentVoteByCommentIdAndUserId(Long postId, Long userId) {
        return this.postVoteRepository.findPostVoteByPostIdAndUserId(postId, userId);
    }

    public PostVote saveAndFlush(PostVote postVote) {
        this.postVoteRepository.saveAndFlush(postVote);
        this.setPostNote(this.postRepository.getById(postVote.getPostId()));
        return postVote;
    }

    public void delete(PostVote postVote) {
        this.postVoteRepository.delete(postVote);
        this.setPostNote(this.postRepository.getById(postVote.getPostId()));
    }

    public void setPostNote(Post comment) {
        comment.setNote(this.postVoteRepository.countAllByPostIdAndUpvote(comment.getId(), true)
                - this.postVoteRepository.countAllByPostIdAndUpvote(comment.getId(), false));
        this.postRepository.saveAndFlush(comment);
    }
}
