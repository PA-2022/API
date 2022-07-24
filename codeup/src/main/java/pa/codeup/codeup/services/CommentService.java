package pa.codeup.codeup.services;

import org.springframework.stereotype.Service;

import pa.codeup.codeup.dto.ContentPost;
import pa.codeup.codeup.entities.Comment;
import pa.codeup.codeup.repositories.CommentRepository;
import pa.codeup.codeup.repositories.ContentPostRepository;

@Service
public class CommentService {
    private final ContentPostRepository contentPostRepository;
    private final CommentRepository commentRepository;

    public CommentService(ContentPostRepository contentPostRepository, CommentRepository commentRepository) {
        this.contentPostRepository = contentPostRepository;
        this.commentRepository = commentRepository;
    }

    public Comment addContentAndComment(Comment comment, ContentPost[] contentPost) {
        Comment currentComment = this.commentRepository.save(comment);

        for (ContentPost content : contentPost) {
            content.setCommentId(currentComment.getId());
            this.contentPostRepository.save(content);
        }

        return currentComment;
    }
    
}
