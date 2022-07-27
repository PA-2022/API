package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.NotificationDao;
import pa.codeup.codeup.jpa.UserForumRelationCompositeKey;

import javax.persistence.*;
import java.io.Serializable;


public class Notification implements Serializable {

	private Long Id;
	private Long userId;
	private String title;
	private String url;
	private boolean isRead;

	public Notification(Long id, Long userId, String title, String url, boolean isRead) {
		Id = id;
		this.userId = userId;
		this.title = title;
		this.url = url;
		this.isRead = isRead;
	}

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
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	public NotificationDao createDao(){
		NotificationDao dao = new NotificationDao();
		dao.setId(this.Id);
		dao.setTitle(this.title);
		dao.setUserId(this.userId);
		dao.setUrl(this.url);
		dao.setRead(this.isRead);
		return dao;
	}
}
