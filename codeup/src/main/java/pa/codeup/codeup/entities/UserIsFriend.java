package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Comment;
import pa.codeup.codeup.dto.CommentVote;
import pa.codeup.codeup.dto.User;

import java.util.Optional;

public class UserIsFriend {
    private User user;
    private boolean isFriend;
    private boolean isAccepted;

    public UserIsFriend(User user, boolean isFriend, boolean isAccepted) {
        this.user = user;
        this.isFriend = isFriend;
        this.isAccepted = isAccepted;
    }

    public User getUser() {
        return user;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
