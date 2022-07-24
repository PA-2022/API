package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.AuthEntity;
import pa.codeup.codeup.dto.User;
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
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public UserController(UserRepository userRepo, AuthRepository authRepository, AuthService authService, UserService userService) {
        this.userRepo = userRepo;
        this.authRepository = authRepository;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public User processRegister(@RequestBody User user) {
        if(this.userRepo.findAllByUsernameLike(user.getUsername()).size() > 0 &&
                this.userRepo.findAllByEmailLike(user.getEmail()).size() > 0) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "User exists");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepo.save(user);
        this.authRepository.save(new AuthEntity(user.getUsername(), "ROLE_USER"));


        return user;
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/current")
    public User getLoggedUser() {
        return this.authService.getAuthUser();
    }

    @GetMapping("/{id}")
    public User getForum(@PathVariable Long id) {
        User user =  userRepo.getUserById(id);

        if(user == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User toUpdate = this.userRepo.getUserById(id);
        AuthEntity authEntity = this.authRepository.getByUsername(toUpdate.getUsername());
        toUpdate.setFirstname(updatedUser.getFirstname());
        toUpdate.setLastname(updatedUser.getLastname());
        toUpdate.setUsername(updatedUser.getUsername());
        toUpdate.setEmail(updatedUser.getEmail());

        toUpdate = this.userRepo.saveAndFlush(toUpdate);
        this.authRepository.save(new AuthEntity(toUpdate.getUsername(), "ROLE_USER"));
        this.authRepository.delete(authEntity);

        return toUpdate;
    }

    @GetMapping("/test")
    public String test() {
        return "<p>Bonjour c'est le endpoint de test JEE (bon y'en as d'autres mais je donne que celui la)&nbsp;" +
                "<br>" +
                "<img src=\"https://en.meming.world/images/en/4/4a/Modern_Problems_Require_Modern_Solutions.jpg\" alt=\"\" /></p>";
    }

    @GetMapping("/username-exists/{username}")
    public boolean usernameExists(@PathVariable String username) {
        return this.userRepo.findAllByUsernameLike(username).size() > 0;
    }
    @GetMapping("/email-exists/{email}")
    public boolean emailExists(@PathVariable String email) {
        return this.userRepo.findAllByEmailLike(email).size() > 0;
    }
    @PostMapping("/send-password-edit")
    public boolean sendPasswordEditMail() {
        User user = this.authService.getAuthUser();
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
        User user = this.authService.getAuthUser();
        Iterator<String> itr = request.getFileNames();
        MultipartFile file = request.getFile(itr.next());
        if(file != null) {
            String link = this.userService.uploadImage(file, user);
            System.out.println("S3 image link after upload : " + link);
            return new ResponseEntity<>(link, HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.OK);

    }
}
