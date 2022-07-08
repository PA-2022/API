package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.PostVote;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
	public PostVote getPostVoteByPostIdAndUserId(Long commentId, Long userId);

    public int countAllByPostIdAndUpvote(Long id, boolean isUpvote);
}
