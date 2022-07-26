package pa.codeup.codeup.dto;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pa.codeup.codeup.entities.Forum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "forum")
public class ForumDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "title", nullable = false, unique = true ,length = 128)
	private String title;

	@NotNull
	@Column(name = "description", nullable = false, columnDefinition="TEXT")
	private String description;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_date")
	private Date lastUpdateDate;

	@Column(name = "color")
	private String color;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date createDate) {
		this.creationDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date modifyDate) {
		this.lastUpdateDate = modifyDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

    public Forum toEntity() {
		return new Forum(this.id, this.title, this.description, this.creationDate, this.lastUpdateDate, this.color);
    }
}
