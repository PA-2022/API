package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.UserForumRelationDao;

import java.util.List;


public interface UserForumRelationRepository extends JpaRepository<UserForumRelationDao, Long> {

    public UserForumRelationDao getByUserIdAndForumId(Long userId, Long forumId);

    List<UserForumRelationDao> getAllByUserId(Long id);
}
