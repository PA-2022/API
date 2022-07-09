package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.entities.Comment;
import pa.codeup.codeup.dto.CommentVote;
import pa.codeup.codeup.repositories.CommentRepository;
import pa.codeup.codeup.repositories.CommentVoteRepository;

import java.util.Optional;

@Service
public class CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentVoteService(CommentVoteRepository commentVoteRepository, CommentRepository commentRepository) {
        this.commentVoteRepository = commentVoteRepository;
        this.commentRepository = commentRepository;
    }

    public Optional<CommentVote> getCommentVoteByCommentIdAndUserId(Long commentId, Long userId) {
        return this.commentVoteRepository.findCommentVoteByCommentIdAndUserId(commentId, userId);
    }

    public CommentVote saveAndFlush(CommentVote commentVote) {
        this.commentVoteRepository.saveAndFlush(commentVote);
        this.setCommentNote(this.commentRepository.getById(commentVote.getCommentId()));
        return commentVote;
    }

    public void delete(CommentVote commentVote) {
        this.commentVoteRepository.delete(commentVote);
        this.setCommentNote(this.commentRepository.getById(commentVote.getCommentId()));
    }

    public void setCommentNote(Comment comment) {
        comment.setNote(this.commentVoteRepository.countAllByCommentIdAndUpvote(comment.getId(), true)
                - this.commentVoteRepository.countAllByCommentIdAndUpvote(comment.getId(), false));
        this.commentRepository.saveAndFlush(comment);
    }
}
