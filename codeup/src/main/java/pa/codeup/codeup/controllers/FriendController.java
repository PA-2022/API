package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.Friend;
import pa.codeup.codeup.entities.UserAndFriend;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.FriendService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("friends")

public class FriendController {


    private final FriendService friendService;
    private final AuthService authService;

    @Autowired
    public FriendController(FriendService friendService, AuthService authService) {
        this.friendService = friendService;
        this.authService = authService;
    }

    @PostMapping("/add-friend/{friendId}")
    @ResponseBody
    public ResponseEntity<Friend> addFriend(@PathVariable @Valid Long friendId) {
        UserDao user = this.authService.getAuthUser();
        if(user == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        try {
            Friend response = this.friendService.addFriend(user.getId(), friendId);
            return new ResponseEntity<Friend>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Error with request");
        }
    }

    @PostMapping("accept-friend/{friendId}")
    @ResponseBody
    public ResponseEntity<Friend> acceptFriend(@PathVariable @Valid Long friendId){
        UserDao user = this.authService.getAuthUser();
        if(user == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        try {
            Friend response = this.friendService.acceptFriend(user.getId(), friendId);
            return new ResponseEntity<Friend>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Error with request");
        }
    }

    @DeleteMapping("delete-friend/{friendId}")
    @ResponseBody
    public ResponseEntity<String> deleteFriend(@PathVariable @Valid Long friendId){
        UserDao user = this.authService.getAuthUser();
        if(user == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        try {
            this.friendService.deleteFriend(user.getId(), friendId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Error with request");
        }
    }

    @GetMapping("is-friend/{friendId}")
    public ResponseEntity<Friend> getIsFriend(@PathVariable @Valid Long friendId){
        UserDao user = this.authService.getAuthUser();
        if(user == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not connected");
        }
        try {
            return new ResponseEntity<>( this.friendService.getFriend(user, friendId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Error with request");
        }
    }

    @GetMapping("list/{userId}")
    public ResponseEntity<List<UserAndFriend>> getFriendList(@PathVariable @Valid Long userId) {
        try {
            return new ResponseEntity<>( this.friendService.getList(userId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Error with request");
        }
    }
}
