package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pa.codeup.codeup.entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	public Comment getCommentById(Long id);

	public int countCommentByPostId(Long postId);

	public List<Comment> getAllByPostId(Long postId);

}
