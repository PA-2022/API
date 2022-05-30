package pa.codeup.codeup.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class AuthEntity {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "authority")
    private String authority;

    public AuthEntity (String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public AuthEntity() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}