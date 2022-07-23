package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.User;

public class PostWithUserAndForum {
    private final Post post;
    private final String user;
    private final String forum;

    public PostWithUserAndForum(Post post, String user, String forum) {
        this.post = post;
        this.user = user;
        this.forum = forum;
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
