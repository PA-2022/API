package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.PostVote;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
	public PostVote getPostVoteByPostIdAndUserId(Long commentId, Long userId);
}
