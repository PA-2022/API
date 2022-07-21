package pa.codeup.codeup.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.Forum;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
	public Forum getForumById(Long id);
	public List<Forum> findByOrderByIdDesc(Pageable pageable);

}
