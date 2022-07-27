package pa.codeup.codeup.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pa.codeup.codeup.dto.ContentPost;

public interface ContentPostRepository extends JpaRepository<ContentPost, Long> {

    public List<ContentPost> findAllByPostIdOrderByPosition(Long postId); // get all content posts for a post
    public List<ContentPost> findAllByCommentIdOrderByPosition(Long commentId); // get all content posts for a comment
    public void deleteAllByPostId(Long postId); // delete all content posts for a post
    public void deleteAllByCommentId(Long commentId); // delete all content posts for a comment
}
    

