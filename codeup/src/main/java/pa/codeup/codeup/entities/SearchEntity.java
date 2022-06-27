package pa.codeup.codeup.entities;

import java.util.List;

public class SearchEntity {

    private List<User> users;
    private List<Post> randomPosts;
    private List<Post> subscribedSubredditsPosts;

    public SearchEntity(List<User> users, List<Post> randomPosts, List<Post> subscribedSubredditsPosts) {
        this.users = users;
        this.randomPosts = randomPosts;
        this.subscribedSubredditsPosts = subscribedSubredditsPosts;
    }
}
