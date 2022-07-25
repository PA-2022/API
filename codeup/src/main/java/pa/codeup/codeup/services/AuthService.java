package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.AuthEntity;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.repositories.AuthRepository;
import pa.codeup.codeup.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    @Autowired
    public AuthService(UserRepository userRepository, AuthRepository authRepository){
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public User getAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return this.userRepository.findByUsername(username);
    }

    public String hasRight() {
        User loggedUser = this.getAuthUser();
        if(loggedUser == null){
            return null;
        }
        return this.authRepository.getByUsername(loggedUser.getUsername()).getAuthority();
    }
}
