package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.UserDao;

public class UserIsFriend {
    private UserDao user;
    private boolean isFriend;
    private boolean isAccepted;

    public UserIsFriend(UserDao user, boolean isFriend, boolean isAccepted) {
        this.user = user;
        this.isFriend = isFriend;
        this.isAccepted = isAccepted;
    }

    public UserDao getUser() {
        return user;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
