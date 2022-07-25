package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Comment;
import pa.codeup.codeup.dto.CommentVoteDao;
import pa.codeup.codeup.dto.User;

import java.util.Optional;

public class CommentWithUser {
    private Comment comment;
    private User user;
    private Optional<CommentVoteDao> commentVote;

    public CommentWithUser(Comment comment, User user, Optional<CommentVoteDao> commentVote) {
        this.comment = comment;
        this.user = user;
        this.commentVote = commentVote;
    }

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public Optional<CommentVoteDao> getOptionalCommentVote() { return commentVote; }
}
