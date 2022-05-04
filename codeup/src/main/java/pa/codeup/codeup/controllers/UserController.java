package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.entities.AuthEntity;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.AuthRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("users")
public class UserController {

    private UserRepository userRepo;
    private AuthRepository authRepository;

    @Autowired
    public UserController(UserRepository userRepo, AuthRepository authRepository) {
        this.userRepo = userRepo;
        this.authRepository = authRepository;
    }

    @PostMapping("/register")
    public User processRegister(@RequestBody User user) {
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return this.userRepo.findByUsername(username);
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
        System.out.println(toUpdate.getUsername());
        System.out.println(authEntity.getUsername());
        this.authRepository.save(new AuthEntity(toUpdate.getUsername(), "ROLE_USER"));
        this.authRepository.delete(authEntity);

        return toUpdate;
    }
}
