package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.UserDao;

public class UserAndFriend {
    private UserDao user;
    private Friend friend;

    public UserAndFriend(UserDao user, Friend friend) {
        this.user = user;
        this.friend = friend;
    }

    public UserDao getUser() {
        return user;
    }

    public Friend getFriend() {
        return friend;
    }
}
