package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pa.codeup.codeup.dto.UserDao;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDao, Long> {
	public UserDao findByEmail(String email);
    public UserDao findByUsername(String username);
	public UserDao getUserById(Long id);
	public List<UserDao> findAllByUsernameLike(@Param("username") String username);
	public List<UserDao> findAllByUsernameLikeAndIdNot(@Param("username") String username, Long userId);
    public List<UserDao> findAllByEmailLike(@Param("email") String email);
	public int countAllByProfilePictureNameLike(@Param("name") String name);
}
