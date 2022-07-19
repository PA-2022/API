package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.PostVote;

import java.util.Optional;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
	public Optional<List<PostVote>> findPostVoteByPostIdAndUserId(Long commentId, Long userId);

    public int countAllByPostIdAndUpvote(Long id, boolean isUpvote);
}
