package pa.codeup.codeup.entities;

import pa.codeup.codeup.dto.UserDao;


public class User {

	private Long id;
	private String email;
	private String password;
	private String username;
	private String firstname;
	private String lastname;
	private boolean enabled = true;
	private String profilePictureUrl;
	private String profilePictureName;

	public User(Long id, String email, String password, String username, String firstname, String lastname, boolean enabled, String profilePictureUrl, String profilePictureName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.profilePictureUrl = profilePictureUrl;
		this.profilePictureName = profilePictureName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}

	public UserDao createDao(){
		UserDao dao = new UserDao();
		dao.setEmail(this.email);
		dao.setFirstname(this.firstname);
		dao.setPassword(this.password);
		dao.setEnabled(this.enabled);
		dao.setProfilePictureName(this.profilePictureName);
		dao.setProfilePictureUrl(this.profilePictureUrl);
		dao.setLastname(this.lastname);
		dao.setUsername(this.username);
		dao.setId(this.id);
		return dao;
	}
}
