package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.CommentVote;
import pa.codeup.codeup.dto.User;

import java.util.Optional;

public class CommentWithUser {
    private Comment comment;
    private User user;
    private Optional<CommentVote> commentVote;

    public CommentWithUser(Comment comment, User user, Optional<CommentVote> commentVote) {
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

    public Optional<CommentVote> getOptionalCommentVote() { return commentVote; }
}
