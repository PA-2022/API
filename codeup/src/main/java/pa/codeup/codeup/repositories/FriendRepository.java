package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.FriendDao;

import java.util.List;


public interface FriendRepository extends JpaRepository<FriendDao, Long> {
    FriendDao findByFriendId(Long friendId);
    FriendDao findByUserId(Long friendId);
    FriendDao findByUserIdAndFriendId(Long userId, Long FriendId);
    List<FriendDao> findByUserIdOrFriendId(Long userId, Long friendId);
}
