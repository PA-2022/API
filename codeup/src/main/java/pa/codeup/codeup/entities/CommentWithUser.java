package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.User;

public class CommentWithUser {
    private Comment comment;
    private User user;

    public CommentWithUser(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
