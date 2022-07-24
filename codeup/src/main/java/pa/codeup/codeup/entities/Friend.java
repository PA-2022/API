package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.FriendDto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Friend {

    private Long id;
    private Long userId;
    private Long friendId;
    private boolean isAccepted;

    public Friend(Long id, Long userId, Long friendId, boolean isAccepted) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.isAccepted = isAccepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public FriendDto toFriendDto() {
        FriendDto dto = new FriendDto();
        dto.setFriendId(this.friendId);
        dto.setAccepted(this.isAccepted);
        dto.setUserId(this.userId);
        dto.setId(this.id);
        return dto;
    }
}
