package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.CommentVote;


public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
	public CommentVote getCommentVoteByCommentIdAndUserId(Long commentId, Long userId);
}
