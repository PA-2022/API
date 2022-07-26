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
    List<Post> findAllByTitleLikeAndForumIdInAndUserIdNot(@Param("title") String title, List<Long> forumId, Long userId);
    List<Post> findAllByTitleLikeAndForumIdNotInAndUserIdNot(@Param("title") String title, List<Long> forumId, Long userId);
    List<Post> findAllByForumId(Long forumId);

    //forum page
    List<Post> findAllByForumIdOrderByCreationDateDesc(Long forumId, Pageable pageable);
    List<Post> findAllByForumIdOrderByNoteDescCreationDateDesc(Long forumId, Pageable pageable);

    //unlogged homepage
    List<Post> findAllByIdNotNullOrderByNoteDescCreationDateDesc(Pageable pageable);
    List<Post> findAllByIdNotNullOrderByCreationDateDesc(Pageable pageable);

    //logged homepage
    List<Post> findAllByUserIdInOrForumIdInOrderByNoteDescCreationDateDesc(List<Long> usersIds, List<Long> forumsIds, Pageable pageable);
    List<Post> findAllByUserIdInOrForumIdInOrderByCreationDateDesc(List<Long> usersIds, List<Long> forumsIds, Pageable pageable);

    //user search
    List<Post> findAllByUserIdOrderByNoteDescCreationDateDesc(Long userId, Pageable pageable);
    List<Post> findAllByUserIdOrderByCreationDateDesc(Long userId, Pageable pageable);
}
