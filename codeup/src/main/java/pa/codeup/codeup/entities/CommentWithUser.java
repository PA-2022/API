package pa.codeup.codeup.entities;


import pa.codeup.codeup.dto.*;

import java.util.List;
import java.util.Optional;


public class CommentWithUser {
    private final Comment comment;
    private final User user;
    private final Optional<CommentVoteDao> commentVote;
    private final List<ContentPost> contents;
    
    public CommentWithUser(Comment comment, User user, Optional<CommentVoteDao> commentVote, List<ContentPost> contents) {
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

    public Optional<CommentVoteDao> getOptionalCommentVote() { return commentVote; }

    public List<ContentPost> getContents() {
        return contents;
    }
}
