package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.dto.User;

import java.util.List;

public class SearchEntity {

    private final List<UserIsFriend> users;
    private final List<PostWithUserAndForum> randomPosts;
    private final List<PostWithUserAndForum> subscribedSubredditsPosts;

    public SearchEntity(List<UserIsFriend> users, List<PostWithUserAndForum> randomPosts, List<PostWithUserAndForum> subscribedSubredditsPosts) {
        this.users = users;
        this.randomPosts = randomPosts;
        this.subscribedSubredditsPosts = subscribedSubredditsPosts;
    }

    public List<UserIsFriend> getUsers() {
        return users;
    }

    public List<PostWithUserAndForum> getRandomPosts() {
        return randomPosts;
    }

    public List<PostWithUserAndForum> getSubscribedSubredditsPosts() {
        return subscribedSubredditsPosts;
    }
}
