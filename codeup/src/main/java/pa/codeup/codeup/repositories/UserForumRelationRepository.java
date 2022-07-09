package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.UserForumRelation;

import java.util.List;


public interface UserForumRelationRepository extends JpaRepository<UserForumRelation, Long> {

    public UserForumRelation getByUserIdAndForumId(Long userId, Long forumId);

    List<UserForumRelation> getAllByUserId(Long id);
}
