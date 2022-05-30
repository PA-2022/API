package pa.codeup.codeup.entities;

import java.util.List;

public class PostComment {
    private CommentWithUser comment;
    private List<CommentWithUser> responses;

    public PostComment(CommentWithUser comment, List<CommentWithUser> responses) {
        this.comment = comment;
        this.responses = responses;
    }

    public CommentWithUser getComment() {
        return comment;
    }

    public void setComment(CommentWithUser comment) {
        this.comment = comment;
    }

    public List<CommentWithUser> getResponses() {
        return responses;
    }

    public void setResponses(List<CommentWithUser> responses) {
        this.responses = responses;
    }
}
