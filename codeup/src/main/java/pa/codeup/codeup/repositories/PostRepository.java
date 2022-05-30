package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.AuthEntity;
import pa.codeup.codeup.entities.Post;


public interface PostRepository extends JpaRepository<Post, Long> {

    public Post getPostById(Long id);
}
