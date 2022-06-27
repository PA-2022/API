package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.AuthEntity;
import pa.codeup.codeup.entities.Post;

import java.util.Collection;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    Post getPostById(Long id);
    void deletePostById(Long postId);
    List<Post> findAllByTitleLikeOrContentLike(String title, String content);
    List<Post> findAllByTitleLikeOrContentLikeAndForumIdIn(String title, String content, Collection<Long> forumId);
}
