package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.CommentVote;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.dto.*;

import java.util.List;
import java.util.Optional;


public class CommentWithUser {
    private Comment comment;
    private User user;
    private Optional<CommentVote> commentVote;
    private List<ContentPost> contents;
    
    public CommentWithUser(Comment comment, User user, Optional<CommentVote> commentVote, List<ContentPost> contents) {
        this.comment = comment;
        this.user = user;
        this.commentVote = commentVote;
        this.contents = contents;
    }

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public Optional<CommentVote> getOptionalCommentVote() { return commentVote; }

    public List<ContentPost> getContents() {
        return contents;
    }
}
