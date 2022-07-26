package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.CommentVoteDao;

import javax.validation.constraints.NotNull;


public class CommentVote {
    private Long id;
    @NotNull
    private boolean upvote;
    private Long userId;
    @NotNull
    private Long commentId;

    public CommentVote(Long id, boolean upvote, Long userId, Long commentId) {
        this.id = id;
        this.upvote = upvote;
        this.userId = userId;
        this.commentId = commentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public CommentVoteDao createDao() {
        CommentVoteDao dao = new CommentVoteDao();
        dao.setCommentId(this.commentId);
        dao.setUpvote(this.upvote);
        dao.setUserId(this.userId);
        dao.setCommentId(this.commentId);
        return dao;
    }
}
