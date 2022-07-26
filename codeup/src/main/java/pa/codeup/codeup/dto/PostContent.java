package pa.codeup.codeup.dto;

public class PostContent {
    Post post;
    ContentPost[] contentPost;

    public PostContent(Post post, ContentPost[] contentPost) {
        this.post = post;
        this.contentPost = contentPost;
    }


    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ContentPost[] getContentPost() {
        return this.contentPost;
    }

    public void setContentPost(ContentPost[] contentPost) {
        this.contentPost = contentPost;
    }

    public String toString() {
        return "PostContent{" +
                "post=" + post +
                ", contentPost=" + contentPost +
                '}';
    }

}
