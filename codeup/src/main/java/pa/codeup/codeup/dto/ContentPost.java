package pa.codeup.codeup.dto;

import javax.persistence.*;

@Entity
@Table(name = "content_posts")
public class ContentPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @Column(name ="post_id")
    private Long postId;

    @Column(name ="comment_id")
    private Long commentId;

    @Column(name = "type")
    private int type;
    
    @Column(name ="position")
    private Long position;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getPosition() {
        return this.position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getCommentId() {
        return this.commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    
    public String toString() {
        return "ContentPost{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postId=" + postId +
                ", type=" + type +
                ", position=" + position +
                '}';
    }

}
