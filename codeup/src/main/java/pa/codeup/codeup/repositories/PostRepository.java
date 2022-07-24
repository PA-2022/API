package pa.codeup.codeup.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pa.codeup.codeup.dto.Post;

import java.util.Arrays;
import java.util.List;

import java.util.Collection;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getPostByForumId(Long forumId);
    Post getPostById(Long id);
    void deletePostById(Long postId);
    List<Post> findAllByTitleLikeOrContentLike(@Param("title") String title, @Param("content") String content);
    List<Post> findAllByTitleLikeOrContentLikeAndForumIdIn(@Param("title") String title,@Param("content") String content, List<Long> forumId);
    List<Post> findAllByTitleLikeOrContentLikeAndForumIdNotIn(@Param("title") String title,@Param("content") String content, List<Long> forumId);
    List<Post> findAllByForumId(Long forumId);
    List<Post> findAllByForumIdOrderByCreationDateDesc(Long forumId, Pageable pageable);
    List<Post> findAllByForumIdOrderByNoteDescCreationDateDesc(Long forumId, Pageable pageable);

}
