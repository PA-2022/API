package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.UserForumRelationDao;
import pa.codeup.codeup.jpa.UserForumRelationCompositeKey;

import javax.persistence.*;
import java.io.Serializable;

public class UserForumRelation implements Serializable {

	private Long userId;
	private Long forumId;

	public UserForumRelation(Long userId, Long forumId) {
		this.userId = userId;
		this.forumId = forumId;
	}

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

	public UserForumRelationDao createDao() {
		UserForumRelationDao dao = new UserForumRelationDao();
		dao.setForumId(this.forumId);
		dao.setUserId(this.userId);
		return dao;
	}
}
