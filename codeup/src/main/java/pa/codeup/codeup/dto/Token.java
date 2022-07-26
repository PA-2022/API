package pa.codeup.codeup.dto;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "token",  unique = true,  nullable = false)
	private String token;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "is_active", nullable = false)
	private boolean isActive = true;

	public Token(Long id, String token, Long userId, boolean isActive) {
		this.id = id;
		this.token = token;
		this.userId = userId;
		this.isActive = isActive;
	}

	public Token() {

	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public Long getUserId() {
		return userId;
	}

	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
