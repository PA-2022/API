package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pa.codeup.codeup.dto.AuthEntity;
import pa.codeup.codeup.dto.NotificationDao;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<NotificationDao, Long> {

    List<NotificationDao> findAllByUserId(Long userId);
    int countAllByUserIdAndReadIsFalse(Long userId);
    List<NotificationDao> findAllByUserIdAndReadIsFalse(Long userId);

}
