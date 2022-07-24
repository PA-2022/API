package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Post;

public class PostWithUserAndForum {
    private final Post post;
    private final String user;
    private final String userImage;
    private final String forum;

    public PostWithUserAndForum(Post post, String user, String userImage, String forum) {
        this.post = post;
        this.user = user;
        this.userImage = userImage;
        this.forum = forum;
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
}
