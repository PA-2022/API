package pa.codeup.codeup.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.UserRepository;

@Controller
@CrossOrigin
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@CrossOrigin
	@PostMapping("/register")
	@ResponseBody
	public User processRegister(@RequestBody User user) {
		System.out.println(user.getEmail());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getPassword());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		
		return user;
	}
	
	@GetMapping("/users")
	@ResponseBody
	public List<User> listUsers() {
		return userRepo.findAll();
	}
}
