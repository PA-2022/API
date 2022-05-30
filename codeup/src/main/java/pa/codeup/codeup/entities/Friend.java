package pa.codeup.codeup.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post_votes")
public class Friend  implements Serializable{
	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Id
	@Column(name = "friend_id", nullable = false)
	private boolean friendId;
	
	@Column(name = "is_accepted", nullable = false)
	private boolean isAccepted;

}
