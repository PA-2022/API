package pa.codeup.codeup.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pa.codeup.codeup.dto.ForumDao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Forum {
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private String description;
	private Date creationDate;
	private Date lastUpdateDate;
	private String color;

	public Forum(Long id, String title, String description, Date creationDate, Date lastUpdateDate, String color) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.color = color != null ? color : "#ffffff";
	}

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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ForumDao createDao() {
		ForumDao dao = new ForumDao();
		dao.setColor(this.color);
		dao.setCreationDate(this.creationDate);
		dao.setLastUpdateDate(this.lastUpdateDate);
		dao.setDescription(this.description);
		dao.setTitle(this.title);
		dao.setId(this.id);
		return dao;
	}
}
