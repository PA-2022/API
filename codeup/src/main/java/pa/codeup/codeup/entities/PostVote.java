package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.PostVoteDao;

import javax.persistence.*;

public class PostVote {
	private Long id;
	private boolean upvote;
	private Long userId;
	private Long postId;

	public PostVote(Long id, boolean upvote, Long userId, Long postId) {
		this.id = id;
		this.upvote = upvote;
		this.userId = userId;
		this.postId = postId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isUpvote() {
		return upvote;
	}

	public void setUpvote(boolean upvote) {
		this.upvote = upvote;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public PostVoteDao createDao() {
		PostVoteDao dao = new PostVoteDao();
		dao.setId(this.id);
		dao.setPostId(this.postId);
		dao.setUserId(this.userId);
		dao.setUpvote(this.upvote);
		return dao;
	}
}
