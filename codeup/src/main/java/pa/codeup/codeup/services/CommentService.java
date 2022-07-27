package pa.codeup.codeup.services;

import javax.swing.text.AbstractDocument.Content;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.stereotype.Service;

import pa.codeup.codeup.dto.Comment;
import pa.codeup.codeup.dto.CommentContent;
import pa.codeup.codeup.dto.ContentPost;
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

    public Comment updateComment(CommentContent newComment, Comment comment) {
        // Comment comment = this.commentRepository.getCommentById(newComment.getComment().getId());

        if(newComment.getContentPost().length <= 0){
            this.commentRepository.deleteCommentById(comment.getId());
        }

        this.contentPostRepository.deleteAllByCommentId(comment.getId());

        for (ContentPost content : newComment.getContentPost()) {
            content.setCommentId(comment.getId());
            this.contentPostRepository.save(content);
        }

        return comment;
    }
    
}
