package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.FriendDao;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.Friend;
import pa.codeup.codeup.entities.Notification;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.entities.UserAndFriend;
import pa.codeup.codeup.repositories.FriendRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Autowired
    public FriendService(FriendRepository friendRepository, UserRepository userRepository, NotificationService notificationService){
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public Friend addFriend(Long currentUserUd, Long friendId) throws Exception {
        UserDao friend = this.userRepository.getUserById(friendId);
        if(friend == null) {
            throw new Exception("cant find friend");
        }
        FriendDao existsFriend = this.friendRepository.findByUserIdAndFriendId(currentUserUd, friendId);
        if(existsFriend != null){
            throw new Exception("friend demand already exists");
        }
        existsFriend = this.friendRepository.findByUserIdAndFriendId(friendId, currentUserUd);
        if(existsFriend != null && !existsFriend.isAccepted()){
            existsFriend.setAccepted(true);
            return this.friendRepository.saveAndFlush(existsFriend).toEntity();
        }
        User user = this.userRepository.getUserById(currentUserUd).toEntity();
        String notificationTitle = "User " + user.getUsername() + " wants to be your friend";
        String notificationUrl = "/account/" + friendId;
        this.notificationService.insertNotification(new Notification(null, friendId, notificationTitle, notificationUrl, false));
        return this.friendRepository.save(new Friend(null, currentUserUd, friendId, false).toFriendDto()).toEntity();
    }

    public Friend acceptFriend(Long currentUserId, Long friendId) throws Exception {
        UserDao friend = this.userRepository.getUserById(friendId);
        if(friend == null) {
            throw new Exception("cant find friend");
        }
        FriendDao existsFriend = this.friendRepository.findByUserIdAndFriendId(friendId, currentUserId);
        if(existsFriend == null){
            throw new Exception("friend demand doesnt exists");
        }
        existsFriend.setAccepted(true);
        return this.friendRepository.saveAndFlush(existsFriend).toEntity();

    }

    public void deleteFriend(Long currentUserId, Long friendId) throws Exception {
        UserDao friend = this.userRepository.getUserById(friendId);
        if(friend == null) {
            throw new Exception("cant find friend");
        }
        FriendDao existsFriendA = this.friendRepository.findByUserIdAndFriendId(friendId, currentUserId);
        FriendDao existsFriendB = this.friendRepository.findByUserIdAndFriendId(currentUserId, friendId);
        if(existsFriendA == null && existsFriendB == null){
            throw new Exception("cant find friend");
        }
        this.friendRepository.delete(Objects.requireNonNullElse(existsFriendA, existsFriendB));
    }

    public Friend getFriend(UserDao currentUser, Long friendId) {
        FriendDao existsFriendA = this.friendRepository.findByUserIdAndFriendId(friendId, currentUser.getId());
        FriendDao existsFriendB = this.friendRepository.findByUserIdAndFriendId(currentUser.getId(), friendId);
        if(existsFriendA == null && existsFriendB == null) {
            return null;
        }
        return Objects.requireNonNullElse(existsFriendA, existsFriendB).toEntity();
    }

    public List<UserAndFriend> getList(Long userId) throws Exception {
        UserDao currentUser = this.userRepository.getUserById(userId);
        if(currentUser == null) {
            throw new Exception("User doesnt exists");
        }
        List<UserAndFriend> userAreFriends = new ArrayList<>();
        List<FriendDao> friendsDtos = this.friendRepository.findByUserIdOrFriendId(currentUser.getId(), currentUser.getId());
        for (FriendDao friendDao : friendsDtos) {
            Friend friend = friendDao.toEntity();
            Long friendId = Objects.equals(friend.getUserId(), currentUser.getId()) ? friend.getFriendId() : friend.getUserId();
            UserDao friendUser = this.userRepository.getUserById(friendId);
            userAreFriends.add(new UserAndFriend(friendUser, friend));
        }
        return userAreFriends;
    }

    public List<Long> getUserFriends(Long userId) throws Exception {
        UserDao currentUser = this.userRepository.getUserById(userId);
        if(currentUser == null) {
            throw new Exception("User doesnt exists");
        }
        List<Long> usersIds = new ArrayList<>();
        List<FriendDao> friendsDtos = this.friendRepository.findByUserIdOrFriendId(currentUser.getId(), currentUser.getId());
        for (FriendDao friendDao : friendsDtos) {
            Friend friend = friendDao.toEntity();
            Long friendId = Objects.equals(friend.getUserId(), userId) ? friend.getFriendId() : friend.getUserId();
            usersIds.add(friendId);
        }
        return usersIds;
    }
}
