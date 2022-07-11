package pa.codeup.codeup.dto;

import pa.codeup.codeup.jpa.UserForumRelationCompositeKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_forum_relation ")
@IdClass(UserForumRelationCompositeKey.class)
public class UserForumRelation implements Serializable {

	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Id
	@Column(name = "forum_id", nullable = false)
	private Long forumId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
}
