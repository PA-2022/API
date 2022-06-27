package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
    public User findByUsername(String username);
	public User getUserById(Long id);
	public List<User> findAllByUsernameLike(String username);
}
