package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Comment;
import pa.codeup.codeup.dto.CommentVoteDao;
import pa.codeup.codeup.dto.UserDao;

import java.util.Optional;

public class CommentWithUser {
    private Comment comment;
    private UserDao user;
    private Optional<CommentVoteDao> commentVote;

    public CommentWithUser(Comment comment, UserDao user, Optional<CommentVoteDao> commentVote) {
        this.comment = comment;
        this.user = user;
        this.commentVote = commentVote;
    }

    public Comment getComment() {
        return comment;
    }

    public UserDao getUser() {
        return user;
    }

    public Optional<CommentVoteDao> getOptionalCommentVote() { return commentVote; }
}
