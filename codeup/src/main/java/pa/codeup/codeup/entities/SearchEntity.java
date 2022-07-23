package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.User;

import java.util.List;

public class SearchEntity {

    private final List<User> users;
    private final List<PostWithUserAndForum> randomPosts;
    private final List<PostWithUserAndForum> subscribedSubredditsPosts;

    public SearchEntity(List<User> users, List<PostWithUserAndForum> randomPosts, List<PostWithUserAndForum> subscribedSubredditsPosts) {
        this.users = users;
        this.randomPosts = randomPosts;
        this.subscribedSubredditsPosts = subscribedSubredditsPosts;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<PostWithUserAndForum> getRandomPosts() {
        return randomPosts;
    }

    public List<PostWithUserAndForum> getSubscribedSubredditsPosts() {
        return subscribedSubredditsPosts;
    }
}
