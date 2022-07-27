package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.Notification;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.services.AuthService;
import pa.codeup.codeup.services.NotificationService;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthService authService;

    @Autowired
    public NotificationController(NotificationService notificationService, AuthService authService) {
        this.notificationService = notificationService;
        this.authService = authService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAll(){
        UserDao currentUser = this.authService.getAuthUser();
        if(currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not logged");
        }
        return new ResponseEntity<>(this.notificationService.getAllByUser(currentUser.toEntity()), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount(){
        UserDao currentUser = this.authService.getAuthUser();
        if(currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not logged");
        }
        return new ResponseEntity<>(this.notificationService.countUserNotifications(currentUser.toEntity()), HttpStatus.OK);
    }
    @PostMapping("/read-all")
    public ResponseEntity<Void> readAll(){
        UserDao currentUser = this.authService.getAuthUser();
        if(currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not logged");
        }
        this.notificationService.readAllNotifications(currentUser.toEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/read/{notificationId}")
    public ResponseEntity<Void> readANotification(@PathVariable Long notificationId){
        UserDao currentUser = this.authService.getAuthUser();
        if(currentUser == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not logged");
        }
        try {
            this.notificationService.readNotification(notificationId);
        }catch (Exception e){
            throw new ResponseStatusException(UNAUTHORIZED, "User cannot read this notification");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
