package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.Comment;
import pa.codeup.codeup.dto.CommentVoteDao;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.CommentVote;
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

    public Optional<CommentVoteDao> getCommentVoteByCommentIdAndUserId(Long commentId, Long userId) {
        return this.commentVoteRepository.findCommentVoteByCommentIdAndUserId(commentId, userId);
    }

    public CommentVoteDao saveAndFlush(CommentVoteDao commentVoteDao) {
        this.commentVoteRepository.saveAndFlush(commentVoteDao);
        this.setCommentNote(this.commentRepository.getById(commentVoteDao.getCommentId()));
        return commentVoteDao;
    }

    public void delete(CommentVoteDao commentVoteDao) {
        this.commentVoteRepository.delete(commentVoteDao);
        this.setCommentNote(this.commentRepository.getById(commentVoteDao.getCommentId()));
    }

    public void setCommentNote(Comment comment) {
        comment.setNote(this.commentVoteRepository.countAllByCommentIdAndUpvote(comment.getId(), true)
                - this.commentVoteRepository.countAllByCommentIdAndUpvote(comment.getId(), false));
        this.commentRepository.saveAndFlush(comment);
    }

    public CommentVote putCommentVote(CommentVote commentVote, UserDao currentUser) {
        commentVote.setUserId(currentUser.getId());
        CommentVoteDao exists = this.getCommentVoteByCommentIdAndUserId(commentVote.getCommentId(), currentUser.getId()).orElse(null);
        if(exists == null) {
            return this.saveAndFlush(commentVote.createDao()).toEntity();
        }
        if(exists.isUpvote() == commentVote.isUpvote()) {
            this.delete(exists);
            return null;
        }
        exists.setUpvote(commentVote.isUpvote());
        return this.saveAndFlush(exists).toEntity();
    }
}
