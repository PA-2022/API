package pa.codeup.codeup.entities;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content", nullable = false, columnDefinition="TEXT")
	private String content;
	
	@Column(name = "user_id", nullable = true, columnDefinition="TEXT")
	private String code;
}
