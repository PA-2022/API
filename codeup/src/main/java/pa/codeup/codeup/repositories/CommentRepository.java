package pa.codeup.codeup.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pa.codeup.codeup.entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	public Comment getCommentById(Long id);

	public int countCommentByPostId(Long postId);

	public List<Comment> getAllByPostId(Long postId);

	public List<Comment> getAllByPostIdAndCommentParentIdIsNull(Long postId);

	@Transactional
	public void deleteCommentById(Long commentId);

	@Transactional
	public void deleteAllByCommentParentId(Long parentId);

	public List<Comment> getAllByCommentParentId(Long parentId);

}
