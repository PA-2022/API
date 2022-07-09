package pa.codeup.codeup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.dto.Post;

import java.util.List;

import java.util.Collection;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getPostByForumId(Long forumId);
    Post getPostById(Long id);
    void deletePostById(Long postId);
    List<Post> findAllByTitleLikeOrContentLike(String title, String content);
    List<Post> findAllByTitleLikeOrContentLikeAndForumIdIn(String title, String content, Collection<Long> forumId);
}
