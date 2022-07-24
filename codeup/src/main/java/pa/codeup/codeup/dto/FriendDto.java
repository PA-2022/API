package pa.codeup.codeup.dto;

import pa.codeup.codeup.entities.Friend;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friends")
public class FriendDto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "friend_id", nullable = false)
	private Long friendId;
	
	@Column(name = "is_accepted", nullable = false)
	private boolean isAccepted;

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

	public Long isFriendId() {
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

	public Friend toEntity() {
		return new Friend(this.id, this.userId, this.friendId, this.isAccepted);
	}
}
