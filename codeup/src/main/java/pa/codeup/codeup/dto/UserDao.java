package pa.codeup.codeup.dto;

import pa.codeup.codeup.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 45)
	private String email;
	
	@Column(nullable = false, length = 64)
	private String password;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "firstname", nullable = false, length = 20)
	private String firstname;
	
	@Column(name = "lastname", nullable = false, length = 20)
	private String lastname;

	@Column(name = "enabled")
	private boolean enabled = true;

	@Column(name = "profile_picture_url")
	private String profilePictureUrl;

	@Column(name = "profile_picture_name")
	private String profilePictureName;

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User toEntity() {
		return new User(this.id, this.email, "", this.username, this.firstname, this.lastname, this.enabled, this.profilePictureUrl, this.profilePictureName);
	}
}
