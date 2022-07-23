package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pa.codeup.codeup.dto.Token;
import pa.codeup.codeup.entities.AuthEntity;


public interface TokenRepository extends JpaRepository<Token, Long> {

    public Token getTokenByTokenEquals(@Param("token") String token);
}
