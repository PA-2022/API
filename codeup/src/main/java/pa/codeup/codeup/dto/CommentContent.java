package pa.codeup.codeup.dto;

import pa.codeup.codeup.entities.Comment;

public class CommentContent {
    Comment comment;
    ContentPost[] contentPost;

    public CommentContent(Comment comment, ContentPost[] contentPost) {
        this.comment = comment;
        this.contentPost = contentPost;
    }


    public Comment getComment() {
        return this.comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public ContentPost[] getContentPost() {
        return this.contentPost;
    }

    public void setContentPost(ContentPost[] contentPost) {
        this.contentPost = contentPost;
    }
    

    
}
