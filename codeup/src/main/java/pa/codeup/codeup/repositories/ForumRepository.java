package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.entities.User;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
	public Forum getForumById(Long id);

}
