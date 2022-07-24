package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.User;

public class UserAndFriend {
    private User user;
    private Friend friend;

    public UserAndFriend(User user, Friend friend) {
        this.user = user;
        this.friend = friend;
    }

    public User getUser() {
        return user;
    }

    public Friend getFriend() {
        return friend;
    }
}
