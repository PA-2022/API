package pa.codeup.codeup.dto;

import pa.codeup.codeup.entities.Notification;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_notification")
public class NotificationDao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "is_read", nullable = false)
	private boolean read;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Notification toEntity() {
		return new Notification(this.Id, this.userId, this.title, this.url, this.read);
	}
}
