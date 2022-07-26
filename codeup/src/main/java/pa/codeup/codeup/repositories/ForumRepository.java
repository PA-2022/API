package pa.codeup.codeup.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.ForumDao;

import java.util.List;

public interface ForumRepository extends JpaRepository<ForumDao, Long> {
	public ForumDao getForumById(Long id);
	public List<ForumDao> findByOrderByIdDesc(Pageable pageable);

}
