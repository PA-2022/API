package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.ForumDao;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.dto.UserForumRelationDao;
import pa.codeup.codeup.entities.UserForumRelation;
import pa.codeup.codeup.repositories.ForumRepository;
import pa.codeup.codeup.repositories.UserForumRelationRepository;

import java.util.List;

@Service
public class UserForumRelationService {

    private final UserForumRelationRepository userForumRelationRepository;
    private final ForumRepository forumRepository;

    @Autowired
    public UserForumRelationService(UserForumRelationRepository userForumRelationRepository, ForumRepository forumRepository) {
        this.userForumRelationRepository = userForumRelationRepository;
        this.forumRepository = forumRepository;
    }

    public UserForumRelation getByUserIdAndForumId(Long id, Long forumId) {
        UserForumRelationDao dao = userForumRelationRepository.getByUserIdAndForumId(id, forumId);
        return dao != null ? dao.toEntity() : null  ;
    }

    public UserForumRelation addRelation(Long forumId, UserDao currentUser) {

        ForumDao forumDao = this.forumRepository.getForumById(forumId);
        if (forumDao == null) {
            return null;
        }
        UserForumRelationDao userForumRelationDao = new UserForumRelationDao();
        userForumRelationDao.setUserId(currentUser.getId());
        userForumRelationDao.setForumId(forumDao.getId());
        return this.userForumRelationRepository.save(userForumRelationDao).toEntity();
    }

    public boolean deleteRelation(Long forumId, Long userId) {
        UserForumRelationDao userForumRelationDao = this.userForumRelationRepository.getByUserIdAndForumId(userId, forumId);
        if (userForumRelationDao == null) {
            return false;
        }

        this.userForumRelationRepository.delete(userForumRelationDao);
        return true;
    }

    public List<UserForumRelationDao> getAllByUserId(Long id) {
        return this.userForumRelationRepository.getAllByUserId(id);
    }
}
