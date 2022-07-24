package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.FriendDto;


public interface FriendRepository extends JpaRepository<FriendDto, Long> {
    FriendDto findByFriendId(Long friendId);
    FriendDto findByUserId(Long friendId);
    FriendDto findByUserIdAndFriendId(Long userId, Long FriendId);
}
