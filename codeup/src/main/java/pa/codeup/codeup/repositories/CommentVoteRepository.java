package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.CommentVote;

import java.util.Optional;


public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
	public Optional<CommentVote> findCommentVoteByCommentIdAndUserId(Long commentId, Long userId);
	public int countAllByCommentIdAndUpvote(Long commentId, boolean isUpvote);
}
