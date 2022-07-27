package pa.codeup.codeup.dto;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String imageUrl;

    @Column(name = "name", nullable = false)
    private String imageName;

    public Images(Long id, String imageUrl, String imageName) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }

    public Images() {

    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
