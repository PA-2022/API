package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Post;

public class PostWithUserAndForum {
    private final Post post;
    private final String user;
    private final String userImage;
    private final String forum;
    private final String forumBgColor;
    private final boolean hasUpvote;
    private final boolean isUpvote;

    public PostWithUserAndForum(Post post, String user, String userImage, String forum, String forumBgColor, boolean hasUpvote, boolean isUpvote) {
        this.post = post;
        this.user = user;
        this.userImage = userImage;
        this.forum = forum;
        this.forumBgColor = forumBgColor;
        this.hasUpvote = hasUpvote;
        this.isUpvote = isUpvote;
    }

    public String getUserImage() {
        return userImage;
    }

    public Post getPost() {
        return post;
    }

    public String getUser() {
        return user;
    }

    public String getForum() {
        return forum;
    }

    public String getForumBgColor() {
        return forumBgColor;
    }

    public boolean isHasUpvote() {
        return hasUpvote;
    }

    public boolean isUpvote() {
        return isUpvote;
    }
}
