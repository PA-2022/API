package pa.codeup.codeup;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.UserRepository;
import pa.codeup.codeup.services.UserService;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
class UserTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    User user = new User(null ,"test@mail.test", "testmdp", "test-user", "test", "user", true, null, null);


    @Test
    void addAndFindUser() throws Exception {
        incrementsUser();
        user = userService.addUser(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void addSameUser() throws Exception {
        Assertions.assertThrows(Exception.class, (Executable) userService.addUser(user));
    }

    void incrementsUser() {
        int count = this.userRepository.findAllByUsernameLike("%"+user.getUsername()+"%").size();
        this.user.setUsername(this.user.getUsername() + (count > 0 ? count :""));
        this.user.setEmail(this.user.getEmail()+ (count > 0 ? count :""));
    }
}
