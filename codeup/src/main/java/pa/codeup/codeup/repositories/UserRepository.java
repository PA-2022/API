package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pa.codeup.codeup.dto.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
    public User findByUsername(String username);
	public User getUserById(Long id);
	public List<User> findAllByUsernameLike(@Param("username") String username);
	public List<User> findAllByUsernameLikeAndIdNot(@Param("username") String username, Long userId);
    public List<User> findAllByEmailLike(@Param("email") String email);
	public int countAllByProfilePictureNameLike(@Param("name") String name);
}
