package pa.codeup.codeup.dto;

import javax.persistence.*;

@Entity
@Table(name = "comment_votes")
public class CommentVote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "upvote", nullable = false)
	private boolean upvote;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "comment_id", nullable = false)
	private Long commentId;

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

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
}
