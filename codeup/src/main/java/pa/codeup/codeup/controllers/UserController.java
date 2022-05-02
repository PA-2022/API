package pa.codeup.codeup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.AuthEntity;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.AuthRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

	private UserRepository userRepo;
	private AuthRepository authRepository;

	@Autowired
	public UserController(UserRepository userRepo, AuthRepository authRepository){
		this.userRepo = userRepo;
		this.authRepository = authRepository;
	}

	@PostMapping("/test")
	public String test(){
		return "test";
	}

	@PostMapping("/register")
	public User processRegister(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		this.authRepository.save(new AuthEntity(user.getUserName(), "ROLE_USER"));


		return user;
	}
	
	@GetMapping("/users")
	public List<User> listUsers() {
		return userRepo.findAll();
	}
}
