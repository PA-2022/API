package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.PostVoteDao;

import java.util.Optional;

public interface PostVoteRepository extends JpaRepository<PostVoteDao, Long> {
	public Optional<PostVoteDao> findPostVoteByPostIdAndUserId(Long commentId, Long userId);

    public int countAllByPostIdAndUpvote(Long id, boolean isUpvote);
}
