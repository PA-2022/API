package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.CommentVoteDao;

import java.util.Optional;


public interface CommentVoteRepository extends JpaRepository<CommentVoteDao, Long> {
	public Optional<CommentVoteDao> findCommentVoteByCommentIdAndUserId(Long commentId, Long userId);
	public int countAllByCommentIdAndUpvote(Long commentId, boolean isUpvote);
}
