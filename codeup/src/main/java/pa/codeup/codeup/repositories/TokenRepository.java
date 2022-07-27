package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pa.codeup.codeup.dto.TokenDao;


public interface TokenRepository extends JpaRepository<TokenDao, Long> {

    public TokenDao getTokenByTokenEquals(@Param("token") String token);
}
