package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.AuthEntity;


public interface AuthRepository extends JpaRepository<AuthEntity, Long> {

    public AuthEntity getByUsername(String username);
}
