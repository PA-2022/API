package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.AuthRepository;
import pa.codeup.codeup.repositories.UserRepository;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.UserService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepo;
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public UserController(UserRepository userRepo, AuthService authService, UserService userService) {
        this.userRepo = userRepo;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> processRegister(@RequestBody UserDao user) {
        if(this.userRepo.findAllByUsernameLike(user.getUsername()).size() > 0 &&
                this.userRepo.findAllByEmailLike(user.getEmail()).size() > 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User exists");
        }
        return new ResponseEntity<>(this.userService.addUser(user), OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        return new ResponseEntity<>(this.userService.findAll(), OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getLoggedUser() {
        UserDao user = this.authService.getAuthUser();
        return new ResponseEntity<>(user != null ? user.toEntity() : null, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = this.userService.getUserById(id);

        if(user == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return new ResponseEntity<>(user, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDao updatedUser) {
        User toUpdate = this.userService.getUserById(id);
        UserDao loggedUser = this.authService.getAuthUser();
        if(loggedUser == null || !toUpdate.getId().equals(loggedUser.getId())){
            throw new ResponseStatusException(UNAUTHORIZED, "Cannot access this user");
        }
        return new ResponseEntity<>(this.userService.updateUser(updatedUser), OK);
    }

    @GetMapping("/test")
    public String test() {
        return "<p>Bonjour c'est le endpoint de test JEE si vous voulez faire des modifs sympa c'est ici :)" +
                "<br>" +
                "<img src=\"https://en.meming.world/images/en/4/4a/Modern_Problems_Require_Modern_Solutions.jpg\" alt=\"\" /></p>";
    }

    @GetMapping("/username-exists/{username}")
    public boolean usernameExists(@PathVariable String username) {
        return this.userService.findAllByUsernameLike(username).size() > 0;
    }
    @GetMapping("/email-exists/{email}")
    public boolean emailExists(@PathVariable String email) {
        return this.userService.findAllByEmailLike(email).size() > 0;
    }
    @PostMapping("/send-password-edit")
    public boolean sendPasswordEditMail() {
        UserDao user = this.authService.getAuthUser();
        if(user == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not logged in");
        }
        return this.userService.sendPasswordChangeEmail(user);
    }
    @PostMapping("/change-password/{password}/token/{token}")
    public boolean changePassword(@PathVariable String password, @PathVariable String token) {
        boolean isOK = this.userService.changePassword(password, token);
        if(!isOK) {
            throw new ResponseStatusException(UNAUTHORIZED, "bad token");
        }
        return true;
    }

    @GetMapping("/token/{token}")
    public boolean isTokenActive(@PathVariable String token) {
        return this.userService.isTokenActive(token);
    }

    @PostMapping("/lost-password/{email}")
    public boolean userLostPassword(@PathVariable String email) {
        return this.userService.emailUserLostPassword(email);
    }

    @PostMapping("/profile-picture")
    public ResponseEntity<String> uploadUserImage(MultipartHttpServletRequest request) throws IOException {
        UserDao user = this.authService.getAuthUser();
        Iterator<String> itr = request.getFileNames();
        MultipartFile file = request.getFile(itr.next());
        if(file != null) {
            String link = this.userService.uploadImage(file, user);
            return new ResponseEntity<>(link, HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.OK);

    }
}
