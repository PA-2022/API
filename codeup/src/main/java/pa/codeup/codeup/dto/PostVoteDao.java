package pa.codeup.codeup.dto;

import pa.codeup.codeup.entities.PostVote;

import javax.persistence.*;

@Entity
@Table(name = "post_votes")
public class PostVoteDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "upvote", nullable = false)
	private boolean upvote;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "post_id", nullable = false)
	private Long postId;

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

	public PostVote toEntity() {
		return new PostVote(this.id, this.upvote, this.userId, this.postId);
	}
}
