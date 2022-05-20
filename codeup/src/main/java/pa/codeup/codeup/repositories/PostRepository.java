package pa.codeup.codeup.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.AuthEntity;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.entities.Post;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    public Post getPostById(Long id);
    public List<Post> findByOrderByIdDesc(Pageable pageable);

}
