package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pa.codeup.codeup.dto.Images;

public interface ImageRepository extends JpaRepository<Images, Long> {
    int countAllByImageNameLike(@Param("name") String name);
}
